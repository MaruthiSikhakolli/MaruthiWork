import pandas as pd
import numpy as np
import os

test_df = pd.read_csv("E:/Education/Tutorials/Data_Science/Hands-On_Projects/001.Titanic-Machine_Learning_from_Disaster/test.csv", sep=",", index_col = 'PassengerId')
train_df = pd.read_csv("E:/Education/Tutorials/Data_Science/Hands-On_Projects/001.Titanic-Machine_Learning_from_Disaster/train.csv", sep=",", index_col = 'PassengerId')

test_df['Survived'] = -888

df = pd.concat((train_df,test_df),axis=0)
df.head()
df.tail()
df.describe(include='all')

df.Fare.mean()
df.Sex.value_counts()
df.Sex.value_counts(normalize=True)
df[df.Survived != -888].Survived.value_counts()

df.Pclass.value_counts().plot(kind='bar', rot = 0, title = 'Class Vs Survived');

#get_ipython().run_line_magic('matplotlib', 'inline')
df.Fare.plot(kind='box')

#Treating missing values
pd.crosstab(df[df.Survived!=-888].Survived, df[df.Survived!=-888].Embarked)
df.groupby(['Pclass','Embarked']).Fare.median()
df.groupby(['Sex','Embarked']).Fare.median()

#Replacing missing values with C
df.Embarked.fillna('C', inplace = True)

#Treating Fare attribute missing values
df[df.Fare.isnull()]
median_fare = df.loc[(df.Pclass==3) & (df.Embarked == 'S'),'Fare'].median()
print(median_fare)
df.Fare.fillna('median_fare', inplace = True)
pd.options.display.max_rows = 15

df[df.Age.isnull()]

#Analyzing name attribute
df.Name
def GetTitle (name):
    first_name_with_title = name.split(',')[1]
    title = first_name_with_title.split('.')[0]
    title = title.strip().lower()
    return title

# Use map function to apply GetTitle function on each row of Name attribute
df.Name.map(lambda x : GetTitle(x)) # Alternatively we can use df.Name.map(GetTitle)
df.Name.map(lambda x : GetTitle(x)).unique()

# Lets modify the GetTitle function
def GetTitle (name):
    title_group = {'mr':'Mr',
                   'mrs':'Mrs',
                   'miss':'Miss',
                   'master':'Master',
                   'don':'Sir',
                   'rev':'Sir',
                   'dr':'Officer',
                   'mme':'Mrs',
                   'ms':'Mrs',
                   'major':'Officer',
                   'lady':'Lady',
                   'sir':'Sir',
                   'mlle':'Miss',
                   'col':'Officer',
                   'capt':'Officer',
                   'the countess':'Lady',
                   'jonkheer':'Sir',
                   'dona':'Lady'
    }
    first_name_with_title = name.split(',')[1]
    title = first_name_with_title.split('.')[0]
    title = title.strip().lower()
    return title_group[title]

# Create a new Title attribute
df['Title'] = df.Name.map(lambda x : GetTitle(x))

title_age_median = df.groupby('Title').Age.transform('median')
df.Age.fillna(title_age_median, inplace = True)

# Outlier treatment

df.loc[df.Age>70]

# Converting Fare data type to float64 from Object
df.Fare = pd.to_numeric(df.Fare, errors='coerce')
df.loc[df.Fare == df.Fare.max()]

# Try some transformations on Fare
logFare = np.log(df.Fare+1.0) #Adding 1 to fare because some of the fares are zero and log(0) becomes undefined

# binning
pd.qcut(df.Fare,4)
pd.qcut(df.Fare,4, labels=['very_low','low','high','very_high'])#discretization
pd.qcut(df.Fare,4, labels=['very_low','low','high','very_high']).value_counts().plot(kind='bar', color='g', rot=0)

# Creating a new feature called 'Fare_Bin'
df['Fare_Bin']=pd.qcut(df.Fare,4,labels=['very_low','low','high','very_high'])

# Feature Engineering
# Age state variable
df['AgeState'] = np.where(df['Age']>=18,'Adult','Child')
# CrossTab for survived and agestate
pd.crosstab(df[df.Survived!=-888].Survived,df[df.Survived!=-888].AgeState)
# Family size feature engineering
df['FamilySize'] = df.Parch + df.SibSp +1 #1 for self
df[df.FamilySize == df.FamilySize.max()]
pd.crosstab(df[df.Survived!=-888].Survived,df[df.Survived!=-888].FamilySize)

# IsMother feature
df['IsMother'] = np.where((df.Sex=='female') & (df.Parch>0) & (df.Age>18) & (df.Title!='Miss'), 1, 0)
df['IsMother'].value_counts()
pd.crosstab(df.Survived[df.Survived!=-888], df.IsMother[df.Survived!=-888])

df[df.Cabin == 'T'] = np.NaN
def Get_Deck (cabin):
    return np.where(pd.notnull(cabin), str(cabin)[0].upper(),'Z')

df['Deck'] = df['Cabin'].map(lambda x : Get_Deck(x))

#Categorical Encoding Techniques
df['IsMale'] = np.where(df.Sex == 'male', 1, 0)

#one-hot encoding
df = pd.get_dummies(df,columns=['Deck','Pclass','Title','Fare_Bin','Embarked','AgeState'])

###Drop columns
#we are dropping those columns because we used these columns already to extract useful information and created
#feature engineering columns
df.drop(['Cabin','Name','Ticket','Parch','SibSp','Sex'], axis=1, inplace=True)
#axis=1 means drop func is applies across columns

###Re-Ordering Columns
columns = [column for column in df.columns if column!='Survived']
columns = ['Survived'] + columns
df = df[columns]

###Saving the processed file
#Train Data
df.loc[df.Survived != -888].to_csv("E:/Education/Tutorials/Data_Science/Hands-On_Projects/001.Titanic-Machine_Learning_from_Disaster/PluralSight/ProcessedData/train.csv")
#Test Data
columns = [column for column in df.columns if column!='Survived']
df.loc[df.Survived == -888, columns].to_csv("E:/Education/Tutorials/Data_Science/Hands-On_Projects/001.Titanic-Machine_Learning_from_Disaster/PluralSight/ProcessedData/test.csv")

#Building the data processing script
get_processed_data_script_file = "E:/Education/Tutorials/Data_Science/Hands-On_Projects/001.Titanic-Machine_Learning_from_Disaster/PluralSight/Reproducible_Script/get_processed_data.py"

#get_ipython().run_cell_magic('writefile', '$get_processed_data_script_file', 'import numpy as np\nimport pandas as pd\nimport os\n\ndef read_data():\n    #Set the path of the raw data\n    train_file_path = "E:/Education/Tutorials/Data_Science/Hands-On_Projects/001.Titanic-Machine_Learning_from_Disaster/train.csv"\n    test_file_path = "E:/Education/Tutorials/Data_Science/Hands-On_Projects/001.Titanic-Machine_Learning_from_Disaster/test.csv"\n    #read the data with all default parameters\n    train_df = pd.read_csv(train_file_path,index_col=\'PassengerId\')\n    test_df = pd.read_csv(test_file_path,index_col=\'PassengerId\')\n    test_df[\'Survived\'] = -888\n    df=pd.concat((train_df,test_df), axis = 0)\n    return df\n\ndef process_data(df):\n    #using the method chaining concept\n    return(df\n           #create title attribute - then add this\n           .assign(Title = lambda x : x.Name.map(get_title))\n           #Work missing values - start with this\n           .pipe(fill_missing_values)\n           #create fare bin feature\n           .assign(Fare_Bin = lambda x : pd.qcut(x.Fare, 4, labels = [\'very_low\', \'low\', \'high\', \'very_high\']))\n           #create age state\n           .assign(AgeState = lambda x : np.where(x.Age >=18, \'Adult\', \'Child\'))\n           .assign(FamilySize = lambda x : x.Parch + x.SibSp + 1)\n           .assign(IsMother = lambda x : np.where((df.Sex==\'female\') & (df.Parch>0) & (df.Age>18) & (df.Title!=\'Miss\'), 1, 0))\n           #create deck feature\n           .assign(Cabin = lambda x : np.where(x.Cabin == \'T\', np.nan, x.Cabin))\n           .assign(Deck = lambda x : x.Cabin.map(get_deck))\n           #Feature encoding\n           .assign(IsMale = lambda x : np.where(x.Sex == \'male\', 1, 0))\n           .pipe(pd.get_dummies, columns=[\'Deck\',\'Pclass\',\'Title\',\'Fare_Bin\',\'Embarked\',\'AgeState\'])\n           #add code to drop unnecessary columns\n           .drop([\'Cabin\',\'Name\',\'Ticket\',\'Parch\',\'SibSp\',\'Sex\'], axis=1)\n           #reorder columns\n           .pipe(reorder_columns)\n          )\n\ndef get_title(name):\n    title_group = {\'mr\':\'Mr\',\n                   \'mrs\':\'Mrs\',\n                   \'miss\':\'Miss\',\n                   \'master\':\'Master\',\n                   \'don\':\'Sir\',\n                   \'rev\':\'Sir\',\n                   \'dr\':\'Officer\',\n                   \'mme\':\'Mrs\',\n                   \'ms\':\'Mrs\',\n                   \'major\':\'Officer\',\n                   \'lady\':\'Lady\',\n                   \'sir\':\'Sir\',\n                   \'mlle\':\'Miss\',\n                   \'col\':\'Officer\',\n                   \'capt\':\'Officer\',\n                   \'the countess\':\'Lady\',\n                   \'jonkheer\':\'Sir\',\n                   \'dona\':\'Lady\'         \n    }\n    first_name_with_title = name.split(\',\')[1]\n    title = first_name_with_title.split(\'.\')[0]\n    title = title.strip().lower()\n    return title_group[title]\n\ndef fill_missing_values(df):\n    #embarked\n    df.Embarked.fillna(\'C\', inplace = True)\n    #fare\n    median_fare = df[(df.Pclass == 3) & (df.Embarked == \'S\')][\'Fare\'].median()\n    df.Fare.fillna(median_fare, inplace = True)\n    #age\n    title_age_median = df.groupby(\'Title\').Age.transform(\'median\')\n    df.Age.fillna(title_age_median, inplace = True)\n    return df\n\ndef reorder_columns(df):\n    columns = [column for column in df.columns if column != \'Survived\']\n    columns = [\'Survived\'] + columns\n    df = df[columns]\n    return df\n\ndef get_deck(cabin):\n    return np.where(pd.notnull(cabin), str(cabin)[0].upper(),\'Z\') \n\ndef reorder_columns(df):\n    columns = [column for column in df.columns if column!=\'Survived\']\n    columns = [\'Survived\'] + columns\n    df = df[columns]\n    return df\n\ndef write_data(df):\n    #Train Data\n    df[df.Survived != -888].to_csv("E:/Education/Tutorials/Data_Science/Hands-On_Projects/001.Titanic-Machine_Learning_from_Disaster/PluralSight/ProcessedData/train.csv")\n    #Test Data\n    columns = [column for column in df.columns if column!=\'Survived\']\n    df[df.Survived == -888][columns].to_csv("E:/Education/Tutorials/Data_Science/Hands-On_Projects/001.Titanic-Machine_Learning_from_Disaster/PluralSight/ProcessedData/test.csv")\n    \nif __name__ == \'__main__\':\n    df = read_data()\n    df = process_data(df)\n    write_data(df)')
#get_ipython().run_line_magic('run', 'E:/Education/Tutorials/Data_Science/Hands-On_Projects/001.Titanic-Machine_Learning_from_Disaster/PluralSight/Reproducible_Script/get_processed_data.py')