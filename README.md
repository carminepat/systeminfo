# SystemInfo

Java library for get information about your machine. Information about Operating System, CPU, Memory, Net, Disk, Network and Services


# getting started

For use this library,
you call class of package: *it.carminepat.systeminfo*. 
All class implement singleton pattern. For example, call instance of **Os** (operating system information) class: `it.carminepat.systeminfo.Os.i();`

## object to Json

All object is ready for json format translation.

    ObjectMapper mapper = new ObjectMapper();
    String jsonOs = mapper.writeValueAsString(Os.i());
    System.out.println(jsonOs);

## Information
 1. Os
 2. CPU
 3. Memory (incomplete)
 4. Net (not-implemented-yet)
 5. Disk (not-implemented-yet)
 6. Services (not-implemented-yet)

## Os information
####  (json format)

    {
    "name":"Mac OS X",  
    "version":"10.15.3",  
    "arch":"x86_64",  
    "country":"IT",  
    "language":"it",  
    "displayCountry":"Italia",  
    "displayLanguage":"italiano",  
    "displayTimeZone":"Ora dell'Europa centrale",  
    "timeZone":"Europe/Rome",  
    "displayDateTime":"08/03/2020 17:34:10",  
    "hostName":"iMac.local",  
    "serialNumber":"XXXXXXX0X000",  
    "hardwareUUID":"000000-XXXXXX-000000",  
    "lastBootTime":"08/03/2020 10:50:00",  
    "fullName":"Mac OS X 10.15.3" 
    }

## Cpu information
#### (json format)

    {
	"processorName":"Quad-Core Intel Core i7",  
	"processorSpeed":"4,2 GHz",  
	"numberOfProcessors":1,  
	"numberOfCores":4,  
	"l3cache":"8 MB",  
	"l2cache":"256 KB"  
	}

