Project - Side Project Member Matching Platform
===

> 사용자가 원하는대로 사이드 프로젝트를 함께할 팀원들을 모집할 수 있고 또는 사용자가 원하는 사이드 프로젝트에 참여하기 위해서 프로젝트를 찾을 수 있는 매칭 플랫폼 프로젝트.

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

### 개발 방법
<details><summary>세부정보</summary>

* 개발과 관련된 모든 이야기는 [Issues](https://github.com/perfect-matching/perfectmatching-backend/issues)에서 진행합니다.

    * 급한 용무는 우선 순위에 따라서 카카오톡 또는 슬랙과 같은 메신저를 이용합니다.

* API 및 모델링 명세를 주기적으로 최신화하면서 `README.md`를 잘 관리합니다.

* **Fork**를 통한 PR을 지향합니다.

* 아래와 같은 Git Workflow를 지향하며 지키려고 노력합니다. ([참고](https://nvie.com/posts/a-successful-git-branching-model/?))

    <img width=750, height=850, src="https://camo.githubusercontent.com/7f2539ff6001fe7700853313e7cdb7fd4602e16a/68747470733a2f2f6e7669652e636f6d2f696d672f6769742d6d6f64656c4032782e706e67">

</details>

### 실행 방법
<details><summary>세부정보</summary>

* 준비사항.

    * Gradle or IntelliJ IDEA

    * JDK (>= 1.8)

    * Spring Boot (>= 2.x)

* 저장소를 `clone`

    ```bash
    $ git clone https://github.com/perfect-matching/perfectmatching-backend.git
    ```

* 프로젝트 내 Project-Matching\src\main\java\com\matching\config 경로에 `HttpConfig.java` 삭제 또는 내용 주석처리.

* DB는 MySQL을 쓴다고 가정.

    * 다른 DB를 사용한다면, 그 DB에 맞게 설정을 해야함.

* 프로젝트 내 Project-Matching\src\main\resources 경로에 `application.yml` 생성.

    * 밑의 양식대로 내용을 채운 뒤, `application.yml`에 삽입.
    <br>

    ```yml
    spring:
        datasource:
            url: jdbc:mysql://localhost:3306/본인_DB?serverTimezone=UTC
            username: 본인_DB_User
            password: 본인_DB_User_Password
            driver-class-name: com.mysql.jdbc.Driver
        jpa:
            hibernate:
                ddl-auto: create

        data:
            web:
                pageable:
                    page-parameter: offset
    ```

* IntelliJ IDEA(>= 2018.3)에서 해당 프로젝트를 `Open`

    * 또는 터미널을 열어서 프로젝트 경로에 진입해서 다음 명령어를 실행.

    * Windows 10

        ```bash
        $ gradlew bootRun
        ```

    * Ubuntu 18.04

        ```
        $ ./gradlew bootRun
        ```

</details>

### REST API URL
<details><summary>세부정보</summary>

* 서버 URL

    * `https://donghun-dev.kro.kr:8083`

* [GET](https://github.com/ssayebee/perfectmatching-backend/blob/master/documents/get.md)

    |URI(자원)| HTTP(행위) | 기능(표현) |
    |:---:|:---:|:---:|
    | `/api/projects` | GET | DB에 있는 Project를 가져오기 위한 api |
    | `/api/projects?offset={num}` | GET | offset에 따른 Project들을 가져오기 위한 api |
    | `/api/projects?location={name}` | GET | location에 따른 Project들을 가져오기 위한 api |
    | `/api/projects?location={name}&offset={num}` | GET | location과 offset에 따른 Project들을 가져오기 위한 api |
    | `/api/projects?position={name}` | GET | position에 따른 Project들을 가져오기 위한 api |
    | `/api/projects?position={name}&offset={num}` | GET | position과 offset에 따른 Project들을 가져오기 위한 api |
    | `/api/projects?position={name}&location={name}` | GET | position과 location에 따른 Project들을 가져오기 위한 api |
    | `/api/projects?location={name}&position&offset={num}` | GET | location과 postion 그리고 offset에 따른 Project들을 가져오기 위한 api |
    | `/api/project/{idx}` | GET | idx에 따른 Project의 정보를 가져오기 위한 api |
    | `/api/project/{idx}/comments` | GET | Project에 따른 Comment들을 가져오기 위한 api |
    | `/api/project/{idx}/members` | GET | Project에 참여중인 맴버들의 정보를 가져오기 위한 api |
    | `/api/project/{idx}/tags` | GET | Project에 포함된 태그들의 정보를 가져오기 위한 api |
    | `/api/profile/{idx}` | GET | idx에 따른 User의 프로필 정보를 가져오기 위한 api |
    | `/api/profile/{idx}/skills` | GET | idx에 따른 User의 프로필 정보 중 스킬 정보를 가져오기 위한 api |
    | `/api/profile/{idx}/projects` | GET | idx에 따른 User의 진행중인 프로젝트 정보를 가져오기 위한 api |
    | `/api/profile/{idx}/doneprojects` | GET | idx에 따른 User의 진행했던 프로젝트 정보를 가져오기 위한 api |
    | `/api/comment/{idx}` | GET | idx에 따른 Comment의 정보를 가져오기 위한 api |
    | `/api/doneproject/{idx}` | GET | idx에 따른 DoneProject를 가져오기 위한 api |
    | `/api/doneproject/{idx}/usedskills` | GET | DoneProject에 포함된 태그들의 정보를 가져오기 위한 api |
    | `/api/tag/{idx}` | GET | idx에 따른 Tag를 가져오기 위한 api |
    | `/api/userskill/{idx}` | GET | idx에 따른 UserSkill을 가져오기 위한 api |
    | `/api/usedskill/{idx}` | GET | idx에 따른 UsedSkill을 가져오기 위한 api |
    | `/api/tags` | GET | DB에 등록되어 있는 Tag들을 가져오기 위한 api |
    | `/api/userskills` | GET | DB에 등록되어 있는 UserSkill들을 가져오기 위한 api |
    | `/api/usedskills` | GET | DB에 등록되어 있는 UsedSkill들을 가져오기 위한 api |

* [POST](https://github.com/ssayebee/perfectmatching-backend/blob/master/documents/post.md)

    |URI(자원)| HTTP(행위) | 기능(표현) |
    |:---:|:---:|:---:|
    | `/api/project` | POST | Project를 생성하기 위한 요청 api |
    | `/api/login` | POST | 서버에 로그인을 요청하기 위한 api |
    | `/api/logout` | POST | 서버에 로그아웃을 요청하기 위한 api |
    | `/api/register` | POST | User 생성을 위해서 회원가입을 요청하는 api |
    | `/api/register/nickcheck` | POST | User 생성을 위해 회원가입시 닉네임 중복 체크를 요청하는 api |
    | `/api/register/emailcheck` | POST | User 생성을 위해 회원가입시 이메일 중복 체크를 요청하는 api |
    | `/api/comment` | POST | Comment를 생성하기 위해 요청하는 api |

* PUT

    |URI(자원)| HTTP(행위) | 기능(표현) |
    |:---:|:---:|:---:|
    | `/api/project/{idx}` | PUT | Project의 idx에 따라 Project를 수정하기 위한 api |
    | `/api/comment/{idx}` | PUT | Comment의 idx에 따라 Comment를 수정하기 위한 api |

* DELETE

    |URI(자원)| HTTP(행위) | 기능(표현) |
    |:---:|:---:|:---:|
    | `/api/project/{idx}` | DELETE | Project의 idx에 따라 Projet를 삭제하기 위한 api |
    | `/api/comment/{idx}` | DELETE | Comment의 idx에 따라 Comment를 삭제하기 위한 api |

</details>
