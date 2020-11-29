# recommender_system
To compare the performance of different implementations(SVD, CUR and collaborative filtering) of recommendation system based on metrics like precision, spearman rank correlation.
Result

**Data** : A corpus of user-movie ratings has been taken from MovieLens, which consists of ratings of 671 users on 1000 movies. 
Rating files are "training.csv" and "test1.csv" which consists of data split in 70:30 ratio. 

**SVD**
In SVD.java,
Ratings from training data is extracted and put in adj[][] array.Ratings from testing data is extracted and put in test[][] array. Then, the transpose of "adj" is calculated and both are multiplied.
Here, " JAMA" package is used, for matrix multiplications and eigenvalue decomposition of rating matrix "adj"(Used .getV() to form eigenvector matrix and .getD() for eigenvalue matrix)

*Some of the complications faced while using the package:*
1. The eigen value matrix that we extracted from package were is ascending order. So, we had to change them descending order, and then according change the order of columns in "U" matrix, and order of rows in "Vt" matrix.
2. Few of the eigenvalues calculated, came out to be negative. So we had to make them zero in order to avoid complex singular values.
Then by application of 90% energy rule, and in the process, few eigen values  are removed and resized the "U" and "Vt" matrix accordingly.

**CUR**
In CUR.java:
The same data set has been used for CUR for both training and testing.
 An object of “Random” class has been created to produce random numbers within a range, which are further used to sample rows and columns.
SVD is performed in a similar process as mentioned above, on the intersection of sampled rows and columns.


**Collaborative Filtering**

<p><a href="https://commons.wikimedia.org/wiki/File:Collaborative_filtering.gif#/media/File:Collaborative_filtering.gif"><img src="https://upload.wikimedia.org/wikipedia/commons/5/52/Collaborative_filtering.gif" alt="Collaborative filtering.gif"></a></p>

*This image shows an example of predicting of the user's rating using collaborative filtering. At first, people rate different items (like videos, images, games). After that, the system is making predictions about user's rating for an item, which the user hasn't rated yet. These predictions are built upon the existing ratings of other users, who have similar ratings with the active user. For instance, in our case the system has made a prediction, that the active user won't like the video*

[*source*: wikipedia]

**Results**

With different evaluation metrics, each implementation has the following characterstics:

<img src="Images/Overall Result.PNG" alt="Hybrid model" style="max-width:100%;">



