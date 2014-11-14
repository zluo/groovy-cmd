/**
 * extract logs
 * @author zluo
 *
 */

class Extract {
	def extract() {
		new File ("c:/temp/server.log").eachLine{it ->

			if (it=~'---') {
			   println it
			}

			if (it=~'END:') {
				def arr = it.split(" ")
				def a1 = arr[5].split("\\.")
				String className=arr[3].substring(1,arr[3].size()-2)
				println "$className.${a1.last()}, ${arr[arr.length-1]}"
			}
		}
	}
}


ex =new Extract()
ex.extract()