# Imports
import pandas as pd
import numpy as np
import os

# Function to get the submission file
def get_submission_file(test_df, model, filename):
    #converting to the matrix
    test_X = test_df.as_matrix().astype('float')
    #Predicting the output
    predictions = model.predict(test_X)
    #Submission dataframe
    df_submission = pd.DataFrame({'PassengerId' : test_df.index, 'Survived': predictions})
    #Submission file name
    raw_path = "E:/Education/Tutorials/Data_Science/Hands-On_Projects/001.Titanic-Machine_Learning_from_Disaster/PluralSight/Submissions";
    submission_file_path = os.path.join(raw_path, filename)
    print (submission_file_path)
    #Writing to CSV
    df_submission.to_csv(submission_file_path, index=False)