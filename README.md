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
|DataBase |MySQL 8.0.3|
|Build Tool |Gradle 5.2.1|

### Modeling

* Entity 모델링 명세 [확인](https://docs.google.com/spreadsheets/d/1kbpWNSX8oapVMX6U6IQtt3sRyn1DrJNmXETlUz-EkQg/edit#gid=0)


* Entity Relation Diagram [확인](https://drive.google.com/file/d/1tmBT3GAL3OIpRocH-hIGdo70-vzptTSo/view)

### API URL
<details><summary>세부정보</summary>

* 서버 URL
        
    * `dongh9508.hopto.org:8083`


|URL| HTTP | 기능 |
|:---:|:---:|:---:| 
| `/api/projects` | GET | DB에 있는 전체 Project를 가져옴. |
| `/api/projects/{idx}` | GET | Project의 idx에 따라 개별로 가져옴. |

</details>