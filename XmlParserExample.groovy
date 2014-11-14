
class WadlParser {
  static def CAR_RECORDS = '''
    <records>
      <car name='HSV Maloo' make='Holden' year='2006'>
        <country>Australia</country>
        <record type='speed'>Production Pickup Truck with speed of 271kph</record>
      </car>
      <car name='P50' make='Peel' year='1962'>
        <country>Isle of Man</country>
        <record type='size'>Smallest Street-Legal Car at 99cm wide and 59 kg in weight</record>
      </car>
      <car name='Royale' make='Bugatti' year='1931'>
        <country>France</country>
        <record type='price'>Most Valuable Car at $15 million</record>
      </car>
    </records>
  '''
  
}

def records = new XmlParser().parseText(WadlParser.CAR_RECORDS)
def allRecords = records.car.size()
assert allRecords == 3
def allNodes = records.depthFirst().size()
assert allNodes == 10
def firstRecord = records.car[0]
assert 'car' == firstRecord.name()
assert 'Holden' == firstRecord.'@make'
assert 'Australia' == firstRecord.country.text()
// 2 cars have an 'e' in the make
assert 2 == records.car.findAll{ it.'@make'.contains('e') }.size()
// makes of cars that have an 's' followed by an 'a' in the country
assert ['Holden', 'Peel'] == records.car.findAll{ it.country.text() =~ '.*s.*a.*' }.'@make'
// types of records
assert ['speed', 'size', 'price'] == records.depthFirst().grep{ it.'@type' }.'@type'
// update to show what would happen if 'New Zealand' bought Holden
firstRecord.country[0].value = ['New Zealand']
assert 'New Zealand' == firstRecord.country.text()
// names of cars with records sorted by year
assert ['Royale', 'P50', 'HSV Maloo'] == records.car.sort{ it.'@year'.toInteger() }.'@name'