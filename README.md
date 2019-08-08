Project - Side Project Member Matching Platform(예명)
===

* 사이드 프로젝트를 함께할 팀원을 구할 수 있는 플랫폼

## 개발환경

|도구|버전|
|:---:|:---:|
| Framework |Spring Boot 2.1.6 |
| OS |Windows 10, Ubuntu 18.04|
|IDE |IntelliJ IDEA Ultimate |
|JDK |JDK 1.8|
|DataBase |MySQL Server 5.7|
|Build Tool |Gradle 5.2.1|

### Modeling

* Entity 모델링 명세 [확인](https://docs.google.com/spreadsheets/d/1kbpWNSX8oapVMX6U6IQtt3sRyn1DrJNmXETlUz-EkQg/edit#gid=0)


* Entity Relation Diagram [확인](https://drive.google.com/file/d/1tmBT3GAL3OIpRocH-hIGdo70-vzptTSo/view)

### API URL
<details><summary>세부정보</summary>

* 서버 URL
        
    * `https://donghun-dev.kro.kr:8083`


|URI(자원)| HTTP(행위) | 기능(표현) |
|:---:|:---:|:---:| 
| `/api/projects` | GET | DB에 있는 Project를 가져오기 위한 api |
| `/api/projects?offset={num}` | GET | offset에 따른 Project들을 가져오기 위한 api |
| `/api/projects?location={name}` | GET | location에 따른 Project들을 가져오기 위한 api |
| `/api/projects?location={name}&offset={num}` | GET | location과 offset에 따른 Project들을 가져오기 위한 api |
| `/api/project/{idx}` | GET | Project의 idx에 따라 개별로 가져오기 위한 api |
| `/api/project` | POST | Project를 생성하기 위한 요청 api |
| `/api/project/{idx}` | PUT | Project의 idx에 따라 Proect의 상세 내용 수정을 위한 api |
| `/api/project/{idx}` | DELETE | Project의 idx에 따라 Proect 삭제를 위한 api |
| `/api/project/{idx}/comments` | GET | Project에 따른 Comment들을 가져오기 위한 api |
| `/api/profile/{idx}` | GET | idx에 따른 User의 프로필 정보를 가져오기 위한 api |
| `/api/comment/{idx}` | GET | idx에 따른 Comment를 가져오기 위한 api |

</details>