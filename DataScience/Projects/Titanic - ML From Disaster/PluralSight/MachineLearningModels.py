import pandas as pd
import numpy as np
import os
from sklearn.linear_model import LogisticRegression
from sklearn.metrics import accuracy_score, confusion_matrix, precision_score, recall_score
from sklearn.model_selection import GridSearchCV
from sklearn.preprocessing import MinMaxScaler, StandardScaler
from ReusableFunctions import get_submission_file
from sklearn import tree

# importing the test and train data frame
train_df = pd.read_csv("E:/Education/Tutorials/Data_Science/Hands-On_Projects/001.Titanic-Machine_Learning_from_Disaster/PluralSight/ProcessedData/train.csv", index_col='PassengerId')
test_df = pd.read_csv("E:/Education/Tutorials/Data_Science/Hands-On_Projects/001.Titanic-Machine_Learning_from_Disaster/PluralSight/ProcessedData/test.csv", index_col='PassengerId')

# Data Preperation
X = train_df.loc[:,'Age':].as_matrix().astype('float')
y = train_df['Survived'].ravel()

# train test split
from sklearn.model_selection import train_test_split
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=0)
print (X_train.shape, y_train.shape)
print (X_test.shape, y_test.shape)

# Feature Normalization
# Normalize Train Data
scaler = MinMaxScaler()
X_train_scaled = scaler.fit_transform(X_train)
X_train_scaled[:,0].min(),X_train_scaled[:,0].max()

# Normalize Test Data
X_test_scaled = scaler.transform(X_test)

# Feature Standadization
# Standadizing both test and train data
scaler = StandardScaler()
X_train_scaled = scaler.fit_transform(X_train)
X_test_scaled = scaler.transform(X_test)

# Create model after normalization and standardization by including Hyper Parameter Optimization
############## Logistic Regression ################
 model_lr = LogisticRegression(random_state=0)
 parameters = {'C':[1.0, 10.0, 50.0, 100.0, 1000.0], 'penalty':['l1', 'l2']}
 clf = GridSearchCV(model_lr, param_grid=parameters, cv=3)
 clf.fit(X_train_scaled, y_train)

 print("Clf Best Parameters : ", clf.best_params_)
 print("Clf Best Score : ", clf.best_score_)
# Evaluate the model
 print('Score for model: ', clf.score(X_test, y_test))

# Model Evaluations
# Model Score
# print("Model score for logistic regression is : ", model_lr.score(X_test,y_test))
# Accurracy Score
# print("Accuracy for logistic regression model :",accuracy_score(y_test, model_lr.predict(X_test)))
# Confusion Matrix
# print('Confusion matrix for logistic regression model : \n', confusion_matrix (y_test, model_lr.predict(X_test)))
# Precision and Recall models
# print('Precision for the logistic regression model : ', precision_score(y_test, model_lr.predict(X_test)))
# print('Recall value for the logistic regression model : ', recall_score(y_test, model_lr.predict(X_test)))

# Get Submission File
 get_submission_file(test_df, clf, '03_LogisticReg.csv')

############## Decision Tree Classifier################
model_dt = tree.DecisionTreeClassifier()
parameters = {'max_depth': [10.0, 20.0, 40.0, 100.0]}
clf = GridSearchCV(model_dt, param_grid=parameters, cv=3)
clf.fit(X_train, y_train)

print("Clf Best Parameters : ", clf.best_params_)
print("Clf Best Score : ", clf.best_score_)
# Evaluate the model
print('Score for model: ', clf.score(X_test, y_test))

# Get Submission File
get_submission_file(test_df, clf, '04_DecisionTree.csv')

############## Random Forest ################
from sklearn.ensemble import RandomForestClassifier
model_rf = RandomForestClassifier()
parameters = {'n_estimators': [10, 50, 100, 200, 500], 'max_depth': [10, 20, 40, 100]}
clf = GridSearchCV(model_rf, param_grid=parameters, cv=3)
clf.fit(X_train, y_train)

print("Clf Best Parameters : ", clf.best_params_)
print("Clf Best Score : ", clf.best_score_)
# Evaluate the model
print('Score for model: ', clf.score(X_test, y_test))

# Get Submission File
get_submission_file(test_df, clf, '05_RandomForest.csv')

############## XG Boost ################
 from xgboost import XGBClassifier
 model_XGB = XGBClassifier()
 model_XGB.fit(X_train, y_train)

# Evaluate the model
 print('Score for model: ', model_XGB.score(X_test, y_test))

# # Get Submission File
 get_submission_file(test_df, model_XGB, '06_XGBoost.csv')
