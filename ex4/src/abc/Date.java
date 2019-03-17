

package abc;

public class Date {
	 // private properties of Date-instances
    private int day;
    private int month;
    private int year;
 
    // a static variable to count the number of instances created
    private static int numberOfInstances = 0;
 
    // "default" constructor: default date 1.1.1970
    public Date() {
        // set the object properties
        this.day = 1;
        this.month = 1;
        this.year = 1970;
 
        // increase the number of instances
        numberOfInstances++;
    }
 
    // constructor with date-parameters
    public Date(int day, int month, int year) {
        // if date is valid then create new object with passed parameters
        if (checkDate(day, month, year)) {
            // set the object properties
            this.day = day;
            this.month = month;
            this.year = year;
        }
        //if the date is invalid create new object with default parameters 1.1.1970
        else {
            this.day = 1;
            this.month = 1;
            this.year = 1970;
        }
 
        // increase the number of instances
        numberOfInstances++;
    }
 
    // constructor which duplicates a date object
    public Date(Date otherDate) {
        // set the object properties
        this.day = otherDate.day;
        this.month = otherDate.month;
        this.year = otherDate.year;
 
        // increase the number of instances
        numberOfInstances++;
    }
 
    // this function changes the date of an existing Date-object
    public boolean setDate(int newDay, int newMonth, int newYear) {
        // check if the new date is a valid date
        // if true, then change the object properties
        if (checkDate(newDay, newMonth, newYear)) {
            this.day = newDay;
            this.month = newMonth;
            this.year = newYear;
            // and return a positive confirmation
            return true;
        }
        // otherwise display an error message and return a negative confirmation
        System.out.println("The date is invalid!");
        return false;
    }
 
    // this function checks if a passed date is a valid date
    public boolean checkDate(int day, int month, int year) {
        // a variable containing the number of days of the "current" month (see below)
        int numOfDays = 0;
 
        switch (month) {
            // for months January, March, May, July, August, October and December set the number of days to 31
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                numOfDays = 31;
                break;
 
            // for months April, June, September and November set the number of days to 30
            case 4:
            case 6:
            case 9:
            case 11:
                numOfDays = 30;
                break;
 
            // February is a special case: we have to check if the current year is a leap year
            // set the number of days to 29 if yes
            case 2:
                if ((year % 4 == 0) && year % 100 != 0 || year % 400 == 0) {
                    numOfDays = 29;
                }
                else {
                    numOfDays = 28;
                }
                break;
        }
 
        // return true if the date is valid
        // return false if the date is invalid
        return (month > 0 && month <= 12) && (day > 0 && day <= numOfDays);
    }
 
    // override the default toString-function and return a string in format dd-mm-yyyy
    public String toString() {
        String day = (this.day < 10) ? "0" + this.day : "" + this.day;
        String month = (this.month < 10) ? "0" + this.month : "" + this.month;
        return day + "." + month + "." + this.year;
    }
 
    // this function checks if the current date is before the passed date
    public boolean isBefore(Date otherDate) {
        // a temporary variable to reduce the amount of code
        boolean isBefore = false;
 
        // first check the year (no need to check the whole date if the years are not equal)
        if (this.year < otherDate.year) {
            isBefore = true;
        }
        // in case the years are equal, we have to compare the months
        else if (this.year == otherDate.year) {
            if (this.month < otherDate.month) {
                isBefore = true;
            }
            // and in case the months are equal, compare the days
            else if (this.month == otherDate.month) {
                if (this.day < otherDate.day) {
                    isBefore = true;
                }
            }
        }
 
        // return true if the current date is before the passed date
        // return false if the current date is behind the passed date
        return isBefore;
    }
 
    // add one day to the Date-object
    public Date addDay() {
        // we need temporary variables for all properties
        // to duplicate the date and not to overwrite the initial Date-object
 
        // increase the day by 1
        int tempDay = this.day + 1;
        int tempMonth = this.month;
        int tempYear = this.year;
 
        // however we have to check if the new date is a valid date
        while (!checkDate(tempDay, tempMonth, tempYear)) {
            // if the date is not valid, this means that the current day number is higher
            // then the number of days in the current month
            // we have to reset the days and to increase the month by 1
 
            // if the current day number is higher then 1, the the day was not reseted in the last loop-run
            // increase the month number
            if (tempDay > 1) {
                tempMonth++;
            }
            // otherwise it was reseted in the last loop-run and the month number is incorrect
            // increase the year
            else {
                tempMonth = 1;
                tempYear++;
            }
            // we reset the day in any case, no need to repeat it twice
            tempDay = 1;
        }
 
        // at the end return a new Date-instance
        return new Date(tempDay, tempMonth, tempYear);
    }
 
    // this static function returns the number of created Date-instances
    // (not the number of existing instances!)
    public static int getNumberOfDateObjects() {
        return numberOfInstances;
    }
}
