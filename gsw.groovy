#!/usr/bin/env groovy
if (args)
{
 def address = "http://www.google.com/finance/getprices?i=1&p=1d&f=d,o,h,l,c,v&df=cpct&q=${args[0]}"
 download(address)
 }
else
{
println "Hello Stranger"
}

def download(address)
{
    def data = new URL(address).getText()
//	data.eachLine{println it.grep(~/^a./)}
}