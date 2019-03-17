clc;
clear;
close all;
% load('dataset.mat');
% pos_features=pos_features';
% load('label_train_block.mat');
% train_features=pos_features(1:16732,:);
% labels=labels(:,2);
% train_labels=labels(1:16732);
% SVMModel = fitcsvm(train_features,train_labels);
load('model.mat');
%size of the stride block
block_size=[400,400];
image=imread('06-C-T0.jpg');
%extracting the coordinates of lips and eyes using openface
pts=csvread('06-C-T0.csv',1,0);
eye_l_x=pts(333);
eye_l_y=pts(401);
eye_r_x=pts(339);
eye_r_y=pts(407);
lips_l_x=pts(345);
lips_l_y=pts(413);
lips_r_x=pts(361);
lips_r_y=pts(429);
stride=100;
%threshold for extracting lips and eyes patches
patch_threshold=125;
%resizing the image to fit stride on the image exactly
image=imresize(image,[size(image,1)-mod(size(image,1),stride),size(image,2)-mod(size(image,2),stride)]);
imshow(image);
patch_number=0;
hold on
for i=1:(size(image,1)-mod(size(image,1),stride)-block_size(1))/stride +1
    for j=1:(size(image,2)-mod(size(image,2),stride)-block_size(2))/stride +1
        block=image(1+stride*(i-1):block_size(1)+stride*(i-1),1+stride*(j-1):block_size(1)+stride*(j-1),:);
        [sift_features,orientation]=sift_extraction(block);
        sift_features=double(sift_features);
        [predicted_label,score] = predict(SVMModel,sift_features);
        for d=1:size(predicted_label,1)
        if predicted_label(d)==1
        patch_number=patch_number+1;
        end 
        end
        
        a=find(predicted_label==1);
        % extracting the coordinates where the predicted label is 1
        pos_features=sift_features(a,:);
        % resizing the coordinates where the predicted label is 1 and
        % applying stride constantly
        pos_orientation=orientation(a,:);
        pos_orientation(:,1)=pos_orientation(:,1)*block_size(1)/128 + stride*(j-1);
        pos_orientation(:,2)=pos_orientation(:,2)*block_size(2)/128 + stride*(i-1);
        % removing predictions from lips and eyes
        for k=1:size(pos_orientation,1)
         if (pos_orientation(k,1)>=x1-1.4*patch_threshold) && (pos_orientation(k,1)<=x1+2.2*patch_threshold) && (pos_orientation(k,2)>=y1-1.6*patch_threshold) && (pos_orientation(k,2)<= y1+0.9*patch_threshold)
             pos_orientation(k,1)=0;
         end
        end
         for k=1:size(pos_orientation,1)
         if (pos_orientation(k,1)>=x2-0.5*patch_threshold) && (pos_orientation(k,1)<=x2+3*patch_threshold) && (pos_orientation(k,2)>=y2-1.6*patch_threshold) && (pos_orientation(k,2)<= y2+0.9*patch_threshold)
             pos_orientation(k,1)=0;
         end
         end
          for k=1:size(pos_orientation,1)
         if (pos_orientation(k,1)>=x3-100) && (pos_orientation(k,1)<=x4+100) && (pos_orientation(k,2)>=y3-1*patch_threshold) && (pos_orientation(k,2)<= y4+1*patch_threshold)
             pos_orientation(k,1)=0;
         end
         end
%             
%         
        perm = 1:size(pos_orientation,1) ;
        if  isempty(perm)==1
                k=1;
        else
        sel  = perm(1:size(perm,1));
%           h1   = vl_plotframe(pos_orientation(sel,:)) ; set(h1,'color','k','linewidth',3) ;
%           h2   = vl_plotframe(pos_orientation(sel,:)) ; set(h2,'color','y','linewidth',2) ;
               plot(pos_orientation(:,1),pos_orientation(:,2),'+','color','k');
        end
    end
    
end