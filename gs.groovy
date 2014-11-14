#!/usr/bin/env groovy
/**
 * Show / Download s information
 * -----------------------------------------------------------------------
 *				
 *				gs -l or gs     list 
 *				gs -u           update
 */
import groovy.json.JsonBuilder;
import groovy.json.JsonSlurper
import groovyx.net.http.RESTClient
new GsImpl().run(args)
//@GrabResolver(name='maven', root='http://http://mvnrepository.com/')
//@Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.7' )
class GsImpl {
	RESTClient client = new RESTClient( 'http://localhost:8080/' )
	Map result=[:]
	def command="l"
	def argIndex = []
	
	def run(args) {
		parse(args)
		processCommand(command, argIndex)
	}

    /**
     * List Command
     */
	void l() {
		def resp = client.get()
		assert resp.status==200
		println resp.getData()
	}
    
	/**
	 * list Command
	 * @param index
	 */
	void l(def args) {
		args.each {println it}
	}

	/**
	 * Parse Command Line
	 * @param args
	 * @return
	 */
	def parse(String[] args) {
		args.each{it ->
			if (it.startsWith('-')){
				command=it.substring(1)
			}
			else {
				argIndex.add(it)
			}
		}
	}

	/**
	 * Dispatch Command
	 * 
	 * @param command
	 * @param argIndex
	 * @return
	 */
	def processCommand(def command, def argIndex) {
		if (argIndex==[]){
			"$command"()
		}
		else {
			"$command"(argIndex)
		}
	}
	def methodMissing(String name, args) {
      println """UnKnown arguments, Usage: 
gs                list directory
gs -u             update
     """
	}
}


