clc;
close all;

dataset_path='/home/govardhan/Desktop/image_blocks/';
label_path='/home/govardhan/Desktop/label/';
list=dir('image_blocks/*.png');
k=1;
% for i=986:size(list,1)
%     i
%     a=imread(strcat('image_blocks/',list(i).name));
    a=imread('11-C-T0_patch.jpg');
    a = imresize(a,[128 128]);
    I= imgaussfilt(a,4);
    levels=1;

% --------------------------------------------------------------------
%                                       Convert the to required format
% --------------------------------------------------------------------
    I = single(rgb2gray(I)) ;

    clf ; imagesc(a)
    axis equal ; axis off ; axis tight ;
    vl_demo_print('sift_basic_1') ;

% --------------------------------------------------------------------
%                                                             Run SIFT
% --------------------------------------------------------------------
    [f,d] = vl_sift(I,'Levels',levels) ;

    hold on ;
    perm =1:size(f,2) ;
    sel  = perm(1:size(perm,2)) ;
    for j=1:size(sel,2)
        j
    h1   = vl_plotframe(f(:,sel(j))) ; set(h1,'color','k','linewidth',3) ;
    prompt = 'What is the label? ';
    x = input(prompt);
     if x==1
            labels(k,1)=k;
            labels(k,2)=1;
        else
            labels(k,1)=k;
            labels(k,2)=0;
           
     end
       k=k+1;  
          
    end
% end

                