## PUT - JSON Data Form Example

* `https://donghun-dev.kro.kr:8083/api/project/{idx}`

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

* `https://donghun-dev.kro.kr:8083/api/comment/{idx}`

  * {idx} 댓글 수정

  * Success : Code 200

  * Fail : Code 400

  ```JSON
  {
    "content" : "작성하고자 하는 댓글",
    "projectIdx" : 1
  }
  ```

* `https://donghun-dev.kro.kr:8083/api/img`

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
      "fileName": "PROFILE_IMG_1568638730292.jpg",
      "fileDownloadUri": "http://localhost:8080/api/img/PROFILE_IMG_1568638730292.jpg",
      "fileType": "image/jpeg",
      "size": 6228
  }
  ```

* `https://donghun-dev.kro.kr:8083/project/{idx}/status?status={name}`

  * 프로젝트의 status를 변경하기 위한 api

  * Success : Code 200

  * Fail : Code 400

* `https://donghun-dev.kro.kr:8083/api/project/matching`

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
