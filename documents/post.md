## POST - JSON Data Form Example

* `https://donghun.dev:8083/api/project`

  * POST Project JSON Form Data Example

  * Success : Code 201

  * Fail : Code 400

  ```JSON
  {
      "title": "프로젝트 인원을 모집합니다.",
      "location": "서울",
      "summary": "~ 이러한 프로젝트의 인원을 찾고 있어요.",
      "content": "이러한 내용을 구성하고 있습니다.",
      "developerRecruits": 2,
      "designerRecruits": 1,
      "plannerRecruits": 4,
      "marketerRecruits": 4,
      "etcRecruits": 4,
      "socialUrl": "https://github.com/testUser/testProject",
      "tags" : [
        {
              "text": "JAVA"
          },
          {
              "text": "Spring"
          },
          {
              "text": "Git"
          }
     ]
  }
  ```


* `https://donghun.dev:8083/api/login`

  * SignIn JSON Form Data Example

  * Success : Code 200

  * Fail : Code 401

  ```JSON
  {
    "username" : "admin@email.com",
    "password" : "113444@#$%abcde"
  }
  ```


* `https://donghun.dev:8083/api/register`

  * SignUp JSON Form Data Example

  * Success : Code 201

  * Fail : Code 400

  ```JSON
  {
     "email" : "admin@gmail.com",
     "password" : "113444@#$%abcde",
     "confirmPassword" : "113444@#$%abcde",
     "nickname" : "admin",
     "summary" : "저는 자바를 공부하고 있는 학생입니다.",
     "investTime" : 3,
     "userSkills" : [
        {
              "text": "JAVA"
          },
          {
              "text": "Spring"
          },
          {
              "text": "Git"
          }
     ]
  }
  ```


* `https://donghun.dev:8083/api/register/nickcheck`

  * SignUp Nickname Validation Check JSON Form Data Example

  * Success : Code 200

  * Fail : Code 400

  ```JSON
  {
    "nick" : "admin"
  }
  ```


* `https://donghun.dev:8083/api/register/emailcheck`

  * SignUp E-mail Validation Check JSON Form Data Example

  * Success : Code 200

  * Fail : Code 400

  ```JSON
  {
    "email" : "admin@email.com"
  }
  ```


* `https://donghun.dev:8083/api/comment`

  * POST Comment JSON Form Data Example

  * Success : Code 201

  * Fail : Code 400

  ```JSON
  {
    "content" : "작성하고자 하는 댓글",
    "projectIdx" : 1
  }
  ```

* `https://donghun.dev:8083/api/project/apply`

  * POST Project Apply JSON Form Data Example

  * Success : Code 201

  * Fail : Code 400

  ```JSON
  {
  "projectIdx" : 1,
  "position" : "개발자",
  "simpleProfile" : "그냥 지원하게 되었습니다."
  }
  ```

* `https://donghun.dev:8083/api/doneproject`

  * POST DoneProject JSON Form Data Example

  * Success : Code 201

  * Fail : Code 400

  ```JSON
  {
      "title": "프로젝트 인원을 모집합니다.",
      "summary": "~ 이러한 프로젝트의 인원을 찾고 있어요.",
      "content": "이러한 내용을 구성하고 있습니다.",
      "socialUrl": "https://github.com/testUser/testProject",
      "startDate": "2019-09-18T05:59:02.378",
      "endDate": "2019-09-18T05:59:02.378",
      "usedSkills" : [
        {
              "text": "JAVA"
        },
        {
              "text": "Spring"
        }
     ]
  }
  ```

* `https://donghun.dev:8083/api/profile/modify/nickcheck`

  * 회원정보 수정 닉네임 중복 체크 api form example

  * Success : Code 200

  * Fail : Code 400

  ```JSON
  {
    "nick" : "admin"
  }
  ```