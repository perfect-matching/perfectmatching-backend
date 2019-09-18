## DELETE - API Document

* `https://donghun-dev.kro.kr:8083/api/project/{idx}`

  * {idx} 프로젝트 삭제

  * Success : Code 200

  * Fail : Code 400

* `https://donghun-dev.kro.kr:8083/api/comment/{idx}`

  * {idx} 댓글 삭제

  * Success : Code 200

  * Fail : Code 400

* `https://donghun-dev.kro.kr:8083/api/project/cancel/{idx}`

  * {idx} UserProject 삭제.

  * Success : Code 200

  * Fail : Code 400

* `https://donghun-dev.kro.kr:8083/api/image`

  * 유저의 기존 프로필 이미지를 삭제하고 기본 이미지로 변경.

  * Success : Code 200

  * Fail : Code 400

  * Response Data

  ```JSON
  {
      "image": "https://donghun-dev.kro.kr:8083/api/image/USER_DEFAULT_IMG.jpg",
  }
  ```

* `https://donghun-dev.kro.kr:8083/api/doneproject/{idx}`

  * {idx} DoneProject 삭제.

  * Success : Code 200

  * Fail : Code 400
