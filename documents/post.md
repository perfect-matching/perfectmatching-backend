* `https://donghun-dev.kro.kr:8083/api/project`

  * POST Project JSON Form Data Example

  * Success : Code 200

  * Fail : Code 403

  ```JSON
  {
      "title": "프로젝트 인원을 모집합니다.",
      "location": "서울",
      "summary": "~ 이러한 프로젝트의 인원을 찾고 있어요.",
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

* `https://donghun-dev.kro.kr:8083/api/login`

  * SignIn JSON Form Data Example

  * Success : Code 200

  * Fail : Code 401

  ```JSON
  {
    "username" : "admin@email.com",
    "password" : "113444@#$%abcde"
  }
  ```



* `https://donghun-dev.kro.kr:8083/api/register`

  * SignUp JSON Form Data Example

  * Success : Code 200

  * Fail : Code 403

  ```JSON
  {
     "email" : "admin@gmail.com",
     "password" : "113444@#$%abcde",
     "confirmPassword" : "113444@#$%abcde",
     "nickname" : "admin",
     "description" : "저는 자바를 공부하고 있는 학생입니다.",
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

* `https://donghun-dev.kro.kr:8083/api/register/nickcheck`

  * SignUp Nickname Validation Check JSON Form Data Example

  * Success : Code 200

  * Fail : Code 403

  ```JSON
  {
    "nick" : "admin"
  }
  ```


* `https://donghun-dev.kro.kr:8083/api/register/emailcheck`

  * SignUp E-mail Validation Check JSON Form Data Example

  * Success : Code 200

  * Fail : Code 403

  ```JSON
  {
    "email" : "admin@email.com"
  }
  ```
