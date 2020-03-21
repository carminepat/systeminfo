# SystemInfo

Java library for get information about your machine. Information about Operating System, CPU, Memory, Net, Disk, Network, Services and Process


# getting started

For use this library,
you call class of package: *it.carminepat.systeminfo*. 
All class implement singleton pattern. For example, call instance of **Os** (operating system information) class: `it.carminepat.systeminfo.Os.i();`

## object to Json

All object is ready for json format translation.

    ObjectMapper mapper = new ObjectMapper();
	System.out.println(mapper.writeValueAsString(Os.i()));

## Information
 1. Os (mac,windows)
 2. CPU (mac,windows)
 3. Memory (windows)
 4. Net (windows)
 5. Disk (windows)
 6. Services (windows)
 7. Process (windows)
 8. Program (windows)

## 1)  Os information
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
Other method of class:

 - String getEnvironmentVariable(String s)
 - Map<String, String> getEnvironmentVariables()
 - setEnvironmentVariable(String key, String value)
 - addToEnvironmentVariable(String key, String value)
 - void restart()
 - void restartAfterSeconds(int seconds)
 - void shutdown()
 - void shutdownAfterSeconds(int seconds)

## 2) Cpu information
#### (json format)

    {
	"processorName":"Quad-Core Intel Core i7",  
	"processorSpeed":"4,2 GHz",  
	"numberOfProcessors":1,  
	"numberOfCores":4,  
	"l3cache":"8 MB",  
	"l2cache":"256 KB"  
	}
## 3) Memory information
#### (json format)

    {
	"physicalTotalInstalled":"7,90 GB",  
	"physicalFree":"1,93 GB",  
	"physicalInUse":"5,97 GB",  
	"virtualTotalInstalled":"12,61 GB",  
	"virtualFree":"1,52 GB",  
	"virtualInUse":"11,08 GB"  
	}
## 4) Net information

    {
       "localIpAddress":"11x.168.0.x",
       "publicIP":"xxx.xxx.xxx.xxx",
       "listNetwork":[
          {
             "description":"Broadcom NetXtreme Gigabit Ethernet",
             "deviceId":1,
             "macAddress":"XX:XX:XX:XX:XX:XX",
             "manufacturer":"Broadcom",
             "name":"Broadcom NetXtreme Gigabit Ethernet",
             "netConnectionId":"Ethernet",
             "productName":"Broadcom NetXtreme Gigabit Ethernet",
             "serviceName":"b57nd60a"
          }
       ]
    }
    
   Other method:
   

 - ### void restartNetwork()
 - ### void flushDNS()

## 5) Disk information
#### (json format)

	{"disks":[{
	"name":"C:",  
	"volumeName":"ABSTRACT",  
	"serial":"XXXX0000",  
	"description":"DISCO RIGIDO LOCALE",  
	"fileSystem":"NTFS",  
	"freeSpace":"5,27 GB",  
	"size":"47,46 GB"  
	},{
	"name":"D:",  
	"volumeName":null,  
	"serial":"XOXOXOXO",  
	"description":"DISCO RIMOVIBILE",  
	"fileSystem":"FAT32",  
	"freeSpace":"3,52 GB",  
	"size":"7,48 GB" }]  
	}

## 6) Service information
Method Services.i().getListOfServices(); return a List of SingleService object.
SingleProcess object is composed from this property: 

    boolean acceptStop;
    String caption;
    String displayName;
    String name;
    String pathName;
    int PID;
    boolean started;
    String startMode;
    String status;
    String state;

### getListOfServices()

#### (json format)

    [{
    "acceptStop":false,  
    "caption":"Servizio router AllJoyn",  
    "displayName":"Servizio router AllJoyn",  
    "name":"AJRouter",  
    "pathName":"C:\\Windows\\system32\\svchost.exe -k LocalServiceNetworkRestricted -p",  
    "started":false,  
    "startMode":"Manual",  
    "status":"OK",  
    "state":"Stopped",  
    "pid":0  
    },  {
    "acceptStop":false,  
    "caption":"Servizio Gateway di livello applicazione",  
    "displayName":"Servizio Gateway di livello applicazione",  
    "name":"ALG",  
    "pathName":"C:\\Windows\\System32\\alg.exe",  
    "started":false,  
    "startMode":"Manual",  
    "status":"OK",  
    "state":"Stopped",  
    "pid":0  
    }, {...} ...]
### getListOfServicesStopped()
### getListOfServicesRunning()
### getListOfServicesAcceptStop()
### getListOfServicesNotAcceptStop()
### getServiceByName(String name)
### getServiceByPID(int PID)
### startService(String name)
### stopService(String name)
### changeStartMode(String name, StartModeType MODE)

## 7) Process information
### getListOfProcess();
Method Process.i().getListOfProcess(); return a List of SingleProcess object.
This List is ordered by memory use desc.
SingleProcess object is composed from this property: 

    String name;
    int pid;
    String nameSession;
    int sessionNumber;
    String momory;
#### (json format)

    [{
    "name":"netbeans64.exe",  
    "pid":11056,  
    "nameSession":" Console",  
    "sessionNumber":1,  
    "momory":"1,21 GB"  
    },  {
    "name":"chrome.exe",  
    "pid":7100,  
    "nameSession":" Console",  
    "sessionNumber":1,  
    "momory":"476,35 MB"  
    }, {...}, 
    .
    .
    .
    ]

### getListOfProcessByName("chrome")
with this method: Process.i().getListOfProcessByName("chrome"); filter process by String "name", for example "chrome". It return List of SingleProcess object.
#### (json format)
    [{
    "name":"chrome.exe",  
    "pid":7100,  
    "nameSession":" Console",  
    "sessionNumber":1,  
    "momory":"462,08 MB"  
    },  {
    "name":"chrome.exe",  
    "pid":3132,  
    "nameSession":" Console",  
    "sessionNumber":1,  
    "momory":"354,26 MB"  
    }, {...},
    .
    .
    .
    ]
### getProcessByPid(7100)
with this method: Process.i().getProcessByPid(7100); filter process by his PID. It return an instance of SingleProcess object.
#### (json format)
    {
    "name":"chrome.exe",  
    "pid":7100,  
    "nameSession":" Console",  
    "sessionNumber":1,  
    "momory":"480,15 MB"  
    }
### getListOfProcessByUser("username")
with this method: Process.i().getListOfProcessByUser("username"); filter process by username.
### getListOfFirstFiveProcess()
with this method: Process.i().getListOfFirstFiveProcess(); filter first five process ordered by use of memory desc.
### getListOfProcessMemoryGTMb(MegaBytesNumber)
with this method: Process.i().getListOfProcessMemoryGTMb(MegaBytesNumber); filter list of process  use great than memory specified in MegaBytes.
### killTask(PID)
with this method: Process.i().killTask(PID); kill process with specified PID

## 8) Program information
information about software application installed in OS.
#### (json format)

    [{    
    "description":"Java 7 Update 79 (64-bit)",  
    "installationDate":"10/03/2020",  
    "installLocation":"C:\\Program Files\\Java\\jre7\\",  
    "installState":"5",  
    "name":"Java 7 Update 79 (64-bit)",  
    "packageCache":"C:\\Windows\\Installer\\46785.msi",  
    "vendor":"Oracle",  
    "version":"7.0.790",  
    "identifyingNumber":"{26A24AE4-039D-4CA4-87B4-2F06417079FF}"  
    
    },{    
    "description":"Java 8 Update 73 (64-bit)",  
    "installationDate":"13/03/2020",  
    "installLocation":"C:\\Program Files\\Java\\jre1.8.0_73\\",  
    "installState":"5",  
    "name":"Java 8 Update 73 (64-bit)",  
    "packageCache":"C:\\Windows\\Installer\\520de2.msi",  
    "vendor":"Oracle Corporation",  
    "version":"8.0.730.2",  
    "identifyingNumber":"{26A24AE4-039D-4CA4-87B4-2F86418073F0}"      
    }, {...}, ...]

