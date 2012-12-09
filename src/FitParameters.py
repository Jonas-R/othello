import pandas as pd
import statsmodels.api as sm

def fit(files):
	for file in files:
		data = pd.read_csv(file, sep = ';')
		print(regress(data['result'], data.ix[:,0:5]))

def regress(yvar, xvars):
	xvars['intercept'] = 1
	result = sm.OLS(yvar, xvars).fit()
	return result.params

fit(['/home/jonas/eclipse_workspace/othello/positions' + str(n) + '.txt' for n in range(16)])