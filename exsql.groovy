/**
 * extract logs
 * @author zluo
 *
 */
class ExtractLog {
	
	def data
	String sql="";
	def outList=["API NAME (ms), Total(ms), Counts, SQL TABLE NAME ,DELETE,SELECT,UPDATE,INSERT"]

	def keywords =['delete':2,'from':1,'update':1,'into':1]

	def clean() {
		data = ['from': [:], 'update': [:], 'delete': [:] , 'into': [:], 'method': [:]]
		sql=""
	}
	
	void extract() {
		clean()
		new File ("c:/temp/server.log").eachLine{
			if (it=~"---") {
				generateResult()
				outList.add(it)
			}
			else if (it =~/sqlonly/) {
				if (sql.length() >0){
					extractSql()
				}
				sql=it;
			}
			else if (sql.length()>0){
				if (!isBegin(it)) {
					sql +=it;
				}
				else{
					extractSql()
				}
			}

			if (it=~'END:') {
				def arr = it.split(" ")
				def a1 = arr[5].split("\\.")
				String className=arr[3].substring(1,arr[3].size()-2)
				String name="$className.${a1.last()} "

				if (data['method'].get(name)==null){
					data['method'].put(name, [])
				}
				data['method'].get(name).add(Integer.valueOf(arr[arr.length-1]))
			}
		}
		generateResult()
		writeToConsole()
		writeToFile()

	}

	public void writeToFile() {
		new File("c:/temp/result.csv").withWriter { out ->
			outList.each { out.println it }
		}
	}
	public void writeToConsole() {
		outList.each { println it }
	}

	def generateResult(){
		//def sqlkeys=keywords.entrySet().plus{key,value ->data[key].keySet()}
		def sqlkeys=data['from'].keySet() + data['update'].keySet() + data['into'].keySet() + data['delete'].keySet()
		def methodKeys=data['method'].keySet()
		def maxIndex=[sqlkeys.size(),methodKeys.size()].max()

		for (int i=0; i<maxIndex; i++){
			def method=(data['method'].size()>i)? findMethods(methodKeys[i]):',,'
			def table=(sqlkeys.size()>i)? sqlkeys[i]:''
            def counts=keywords.collect{key, value -> findCount(key, table)}.join(",")
			outList.add("${method},${table},${counts}")
		}
		addTotal()
		clean()
	}

	def findCount(key, table){
		(data[key].get(table))? "${data[key].get(table)}":''
	}
	
	def addTotal(){
		def totaldataTime = data['method'].entrySet().sum{it.value.sum()}
		if (totaldataTime==null) totaldataTime=0
		double t= totaldataTime/1000.0
		outList.add("Total,${t.round(2)}s,,,${sum(data['delete'])},${sum(data['from'])},${sum(data['update'])},${sum(data['into'])}")
	}

	def sum(Map map){
		map.entrySet().sum{it.value}
	}

	def findMethods(key){
		def list= data['method'].get(key)
		def sum= list.sum()
		double avg=sum/list.size()

		return "${key} (${avg.round(2)} ms), ${sum}, ${list.size()}"
	}

	def extractSql(){
		def arr =sql.split(" ")
		keywords.each{key, value ->
			if (!find (arr, key, data.getAt(key),value)) return
		}
		sql=""
	}

	boolean find(String[] arr , String pattern , Map maps, offset){
		for(int i=0; i< arr.length; i++)
		{
			if (arr[i]==pattern)
			{
				def table = arr[i+offset];
				def count = maps.get(table)
				maps.put(table, (count==null)?1:++count)
				return true;
			}
		}
		return false;
	}

	boolean isBegin(String line) {
		String[] arr = line.split()
		if (arr.length==0) return false;
		return (arr[0]==~/\d+:\d+:\d+,\d+/)
	}
}

// Run the script
new ExtractLog().extract()

