#!/usr/bin/env groovy
boolean  verbose=false;
if (args) {
	args.each{ run(it) }
}
else {
	new File(".gswlist").eachLine{run(it)}
}

def run(String ticker)
{
	formatGoogleSimpleResult(download("http://www.google.com/finance/getprices?i=60&p=1d&f=d,o,h,l,c,v&df=cpct&q=${ticker}"), ticker)
}


def download(address) {
	return  new URL(address).getText()
//	return  new File('c:/temp/amd.txt').getText()
}

def formatGoogleSimpleResult(data, ticker) {
	def l=[]
	def a=null
	def startTime
	Float open
	Float close
	Float high=1000
	Float low=0
	long currentTime=0
	Float volumn=0
	
	data.eachLine{it ->
		if (it =~/^a/) {
			a = it.split(",")
			startTime=Long.valueOf(a[0].substring(1))*1000
			close=Float.valueOf(a[1])
			high=Float.valueOf(a[2])
			low=Float.valueOf(a[3])
			open=Float.valueOf(a[4])
			volumn +=Long.valueOf(a[5])
			
		}
		else if (a !=null){
			def a1=it.split(",")
			currentTime=Long.valueOf(a1[0])*60*1000
			close=Float.valueOf(a1[1])
			high=Math.max(high,Double.valueOf(a1[2]))
			low=Math.min(low, Double.valueOf(a1[3]))
			volumn +=Float.valueOf(a1[5])
		}
	}
	
	def time =new Date((startTime + currentTime)).format("yyyy-M-d hh:mm")
	println """[${time}][${ticker}]${close}@${(volumn/1000000).round(1)}m (${(close - open).round(2)}/${(((close-open)/close)*100).round(2)}% ${open}|${low}|${high}) """
}


