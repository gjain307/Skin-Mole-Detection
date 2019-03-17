function [sift_features,h]=sift_extraction(a)


    a = imresize(a,[128 128]);
    I= imgaussfilt(a,7.5);
    levels=1;
    I = single(rgb2gray(I)) ;

% --------------------------------------------------------------------
%                                                             Run SIFT
% --------------------------------------------------------------------
[f,d] = vl_sift(I,'Levels',levels) ;
sift_features=d';
h=f';

end