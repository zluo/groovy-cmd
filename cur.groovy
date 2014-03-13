#!/usr/bin/env groovy
boolean  verbose=false;
if (args) {
	args.each{ run(it) }
}
else {
	new File(".curlist").eachLine{run(it)}
}

def run(String ticker)
{
	download("http://download.finance.yahoo.com/d/quotes.csv?s=${ticker}=X&f=nl1d1t1")
}


def download(address) {
	println  new URL(address).getText()
}


