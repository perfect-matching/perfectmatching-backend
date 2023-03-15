## PUT - JSON Data Form Example

* `http://localhost:8080/api/project/{idx}`

  * {idx} 프로젝트 수정

  * Success : Code 200

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

* `http://localhost:8080/api/comment/{idx}`

  * {idx} 댓글 수정

  * Success : Code 200

  * Fail : Code 400

  ```JSON
  {
    "content" : "작성하고자 하는 댓글",
    "projectIdx" : 1
  }
  ```

* `http://localhost:8080/api/image`

  * 요청 유저의 프로필 사진 수정

  * Success : Code 200

  * Fail : Code 400

  * Request Data(form-data)
  ```
  {
      "file": "PROFILE_IMG_1568638730292.jpg",
  }
  ```

  * Response Data

  ```JSON
  {
      "image": "http://localhost:8080/api/image/PROFILE_IMG_1568638730292.jpg",
  }
  ```

* `http://localhost:8080/project/{idx}/status?status={name}`

  * 프로젝트의 status를 변경하기 위한 api

  * Success : Code 200

  * Fail : Code 400

* `http://localhost:8080/api/project/matching`

  * 프로젝트의 status를 변경하기 위한 api

  * Success : Code 200

  * Fail : Code 400

  ```JSON
  {
  "projectIdx" : 1,
  "userIdx" : 39,
  "status" : "매칭"
  }
  ```

* `http://localhost:8080/api/doneproject/1`

  * {idx} DoneProject 수정.

  * Success : Code 200

  * Fail : Code 400

  ```JSON
  {
      "title": "프로젝트 인원을 모집합니다. (수정됨)",
      "summary": "~ 이러한 프로젝트의 인원을 찾고 있어요. (수정됨)",
      "content": "이러한 내용을 구성하고 있습니다. (수정됨)",
      "socialUrl": "https://github.com/testUser/testProject",
      "startDate": "2019-09-18T05:59:02.378",
      "endDate": "2019-09-18T05:59:02.378",
      "usedSkills" : [
        {
              "text": "JAVA"
        }
     ]
  }
  ```

* `http://localhost:8080/api/profile/{idx}`

  * {idx} User 수정.

  * Success : Code 200

  * Fail : Code 400

  ```JSON
  {     
      "email": "testuser@gmail.com",
      "nickname": "테스트 유저",
      "summary": "~ 이러한 사람입니다.",
      "socialUrl": "https://github.com/testUser/testProject",
      "profileImage": "프로필 이미지",
      "investTime": 5,
      "userSkills" : [
        {
              "text": "JAVA"
        },
        {
              "text": "Spring"
        }
     ]
  }
  ```
