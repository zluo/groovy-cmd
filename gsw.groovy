#!/usr/bin/env groovy
if (args)
{
args.each{
 println "=================== ${it} ======================"
 def address = "http://www.google.com/finance/getprices?i=1&p=1d&f=d,o,h,l,c,v&df=cpct&q=${it}"
 download(address)
 }
 }
else
{
println "Need Ticker"
}

def download(address)
{
    def data = new URL(address).getText()
    data.eachLine{it ->
	if (it =~/^a/)
	{
	    def a = it.split(",")
		
		a[0]= new Date((Long.valueOf(a[0].substring(1))*1000)).format("yyyy-M-d hh:mm")
		println a
	}
	}
}

