#!/usr/bin/env groovy
def data=".curlist"
if (args) {
	args.each{ run(it) }
}
else {
	def cmd_home=System.getenv().get("GROOVY_CMD_HOME");
	if (cmd_home !=null)
	{
		data=cmd_home + "/" + data
	}
	new File(data).eachLine{run(it)}
}

def run(String ticker)
{
	download("http://download.finance.yahoo.com/d/quotes.csv?s=${ticker}=X&f=nl1d1t1")
}


def download(address) {
	println  new URL(address).getText()
}


