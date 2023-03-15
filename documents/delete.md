## DELETE - API Document

* `http://localhost:8080/api/project/{idx}`

  * {idx} 프로젝트 삭제

  * Success : Code 200

  * Fail : Code 400

* `http://localhost:8080/api/comment/{idx}`

  * {idx} 댓글 삭제

  * Success : Code 200

  * Fail : Code 400

* `http://localhost:8080/api/project/cancel/{idx}`

  * {idx} UserProject 삭제.

  * Success : Code 200

  * Fail : Code 400

* `http://localhost:8080/api/image`

  * 유저의 기존 프로필 이미지를 삭제하고 기본 이미지로 변경.

  * Success : Code 200

  * Fail : Code 400

  * Response Data

  ```JSON
  {
      "image": "http://localhost:8080/api/image/USER_DEFAULT_IMG.jpg",
  }
  ```

* `http://localhost:8080/api/doneproject/{idx}`

  * {idx} DoneProject 삭제.

  * Success : Code 200

  * Fail : Code 400
