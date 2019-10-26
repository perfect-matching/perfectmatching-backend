## GET - JSON Data Form Example

* `https://donghun:8083/api/projects` 

  + 프로젝트 12개의 정보 출력.

  + Success : Code 200

  + Fail : Code 400

  

``` JSON
  {
    "_embedded": {
      "datas": [
        {
          "projectIdx": 200,
          "leader": "user_1",
          "leaderIdx": 8,
          "leaderImage": "Profile Image URL",
          "title": "이러 이러한 Side Project 의 함께할 사람들을 찾고 있습니다. 200",
          "summary": "이러한 프로젝트에 참여할 인원을 모집합니다.",
          "location": "울산",
          "developerRecruits": true,
          "designerRecruits": true,
          "plannerRecruits": true,
          "marketerRecruits": true,
          "etcRecruits": false,
          "createdDate": "2019-09-06T17:44:03",
          "status": "진행중",
          "tags": [
            {
              "idx": 8,
              "text": "Spring"
            }
          ],
          "_links": {
            "self": {
              "href": "https://donghun.dev:8083/api/project/200"
            },
            "Leader Profile": {
              "href": "https://donghun.dev:8083/api/profile/8"
            }
          }
        },
        {
          "projectIdx": 199,
          "leader": "testUser_32",
          "leaderIdx": 32,
          "leaderImage": "Profile Image URL",
          "title": "이러 이러한 Side Project 의 함께할 사람들을 찾고 있습니다. 199",
          "summary": "이러한 프로젝트에 참여할 인원을 모집합니다.",
          "location": "충청북도",
          "developerRecruits": true,
          "designerRecruits": true,
          "plannerRecruits": true,
          "marketerRecruits": true,
          "etcRecruits": true,
          "createdDate": "2019-09-06T17:44:03",
          "status": "모집중",
          "tags": [
            {
              "idx": 75,
              "text": "JAVA"
            }
          ],
          "_links": {
            "self": {
              "href": "https://donghun.dev:8083/api/project/199"
            },
            "Leader Profile": {
              "href": "https://donghun.dev:8083/api/profile/32"
            }
          }
        },
        ...
      ]
    },
    "_links": {
      "self": {
        "href": "https://donghun.dev:8083/api/projects"
      },
      "next": {
        "href": "https://donghun.dev:8083/api/projects?offset=1"
      }
    },
    "page": {
      "size": 12,
      "totalElements": 200,
      "totalPages": 17,
      "number": 0
    }
  }
  ```

* `https://donghun.dev:8083/api/projects?offset=1` 

  + 데이터 출력은 `/api/projects` 요청시와 동일하고 다음 항목들을 가져옴.

  + Success : Code 200

  + Fail : Code 400

* `https://donghun.dev:8083/api/projects?location=SEOUL` 

  + 데이터 출력은 `/api/projects` 요청시와 동일하고 지역 정보가 서울인 항목을 가져옴.

  + Success : Code 200

  + Fail : Code 400

* `https://donghun.dev:8083/api/projects?location=SEOUL&offset=1` 

  + 데이터 출력은 `/api/projects` 요청시와 동일하고 지역 정보가 서울인 항목의 다음 정보들을 가져옴.

  + Success : Code 200

  + Fail : Code 400

* `https://donghun.dev:8083/api/projects?position=DEVELOPER` 

  + 데이터 출력은 `/api/projects` 요청시와 동일하고 포지션 정보가 developer를 포함하고 있는 항목의 정보를 가져옴.

  + Success : Code 200

  + Fail : Code 400

* `https://donghun.dev:8083/api/projects?position=DEVELOPER&offset=1` 

  + 데이터 출력은 `/api/projects` 요청시와 동일하고 포지션 정보가 developer를 포함하고 있는 다음 항목 정보들을 가져옴.

  + Success : Code 200

  + Fail : Code 400

* `https://donghun.dev:8083/api/projects?position=DEVELOPER&location=SEOUL` 

  + 데이터 출력은 `/api/projects` 요청시와 동일하고 포지션 정보가 developer를 포함하고 지역 정보가 서울인 항목의 정보를 가져옴.

  + Success : Code 200

  + Fail : Code 400

* `https://donghun.dev:8083/api/projects?position=DEVELOPER&location=SEOUL&offset=1` 

  + 데이터 출력은 `/api/projects` 요청시와 동일하고 포지션 정보가 developer를 포함하고 지역 정보가 서울인 항목의 다음 정보들을 가져옴.

  + Success : Code 200

  + Fail : Code 400

* `https://donghun.dev:8083/api/project/1` 

  + 프로젝트 {idx}의 상세 정보를 가져옴.

  + Success : Code 200

  + Fail : Code 400

  

``` JSON
  {
    "projectIdx": 1,
    "title": "프로젝트 인원을 모집합니다.",
    "leaderIdx": 22,
    "leader": "admin",
    "content": "스프링을 이용한 웹어플리케이션을 제작하려고 합니다.",
    "status": "진행중",
    "location": "서울",
    "createdDate": "2019-09-06T17:44:03",
    "modifiedDate": null,
    "developerRecruits": 4,
    "designerRecruits": 1,
    "plannerRecruits": 1,
    "marketerRecruits": 1,
    "etcRecruits": 1,
    "currentDeveloper": 0,
    "currentDesigner": 0,
    "currentPlanner": 0,
    "currentMarketer": 1,
    "currentEtc": 0,
    "summary": "~ 이러한 프로젝트의 인원을 찾고 있어요.",
    "socialUrl": "https://github.com/testUser/testProject",
    "tags": [
        {
            "idx": 95,
            "text": "JAVA"
        },
        {
            "idx": 36,
            "text": "Spring"
        },
        {
            "idx": 56,
            "text": "Git"
        },
        {
            "idx": 76,
            "text": "MySQL"
        }
    ],
    "_links": {
        "self": {
            "href": "https://donghun.dev:8083/api/project/1"
        },
        "Leader Profile": {
            "href": "https://donghun.dev:8083/api/profile/22"
        },
        "Comments": {
            "href": "https://donghun.dev:8083/api/project/1/comments"
        },
        "Members": {
            "href": "https://donghun.dev:8083/api/project/1/members"
        },
        "Tags": {
            "href": "https://donghun.dev:8083/api/project/1/tags"
        }
    }
  }
  ```

* `https://donghun.dev:8083/api/project/1/comments` 

  + 프로젝트 {idx}의 댓글들을 가져옴.

  + Success : Code 200

  + Fail : Code 400

  

``` JSON
  {
    "_embedded": {
        "datas": [
            {
                "commentIdx": 47,
                "userName": "testUser_35",
                "userIdx": 35,
                "projectTitle": "이러 이러한 Side Project 의 함께할 사람들을 찾고 있습니다. 1",
                "projectIdx": 1,
                "content": "테스트 댓글 47",
                "createdDate": "2019-09-06T17:44:08",
                "modifiedDate": null,
                "_links": {
                    "self": {
                        "href": "https://donghun.dev:8083/api/comment/47"
                    }
                }
            },
            {
                "commentIdx": 219,
                "userName": "testUser_25",
                "userIdx": 25,
                "projectTitle": "이러 이러한 Side Project 의 함께할 사람들을 찾고 있습니다. 1",
                "projectIdx": 1,
                "content": "테스트 댓글 219",
                "createdDate": "2019-09-06T17:44:08",
                "modifiedDate": null,
                "_links": {
                    "self": {
                        "href": "https://donghun.dev:8083/api/comment/219"
                    }
                }
            }
        ]
    },
    "_links": {
        "self": {
            "href": "https://donghun.dev:8083/api/project/1/comments"
        }
    }
  }
  ```

* `https://donghun.dev:8083/api/project/1/members` 

  + 프로젝트 {idx}의 구성원들의 정보를 가져옴.

  + Success : Code 200

  + Fail : Code 400

  

``` JSON
  {
    "_embedded": {
        "datas": [
            {
                "memberIdx": 22,
                "projectIdx": 1,
                "memberNick": "testUser_22",
                "position": "마케터",
                "_links": {
                    "Profile": {
                        "href": "https://donghun.dev:8083/api/profile/22"
                    }
                }
            }
        ]
    },
    "_links": {
        "self": {
            "href": "https://donghun.dev:8083/api/project/1/members"
        }
    }
  }
  ```

* `https://donghun.dev:8083/api/project/1/tags` 

  + 프로젝트 {idx}의 태그들의 정보를 가져옴.

  + Success : Code 200

  + Fail : Code 400

  

``` JSON
  {
    "_embedded": {
        "datas": [
            {
                "idx": 36,
                "text": "테스트 태그 36",
                "_links": {
                    "self": {
                        "href": "https://donghun.dev:8083/api/tag/36"
                    }
                }
            },
            {
                "idx": 56,
                "text": "테스트 태그 56",
                "_links": {
                    "self": {
                        "href": "https://donghun.dev:8083/api/tag/56"
                    }
                }
            },
            {
                "idx": 76,
                "text": "테스트 태그 76",
                "_links": {
                    "self": {
                        "href": "https://donghun.dev:8083/api/tag/76"
                    }
                }
            },
            {
                "idx": 95,
                "text": "테스트 태그 95",
                "_links": {
                    "self": {
                        "href": "https://donghun.dev:8083/api/tag/95"
                    }
                }
            }
        ]
    },
    "_links": {
        "self": {
            "href": "https://donghun.dev:8083/api/project/1/tags"
        }
    }
  }
  ```

* `https://donghun.dev:8083/api/profile/1` 

  + 유저의 {idx}의 프로필 정보를 가져옴.

  + Success : Code 200

  + Fail : Code 400

  

``` JSON
  {
    "userIdx": 1,
    "nickname": "testUser_1",
    "summary": "저는 이러한 사람입니다.",
    "email": "test1@email.com",
    "profileImg": "Profile Image URL",
    "investTime": 4,
    "socialUrl": "https://github.com/testUser",
    "_links": {
        "self": {
            "href": "https://donghun.dev:8083/api/profile/1"
        },
        "Profile Skills": {
            "href": "https://donghun.dev:8083/api/profile/1/skills"
        },
        "Processing Projects": {
            "href": "https://donghun.dev:8083/api/profile/1/projects"
        },
        "Done Projects": {
            "href": "https://donghun.dev:8083/api/profile/1/doneprojects"
        }
    }
  }
  ```

* `https://donghun.dev:8083/api/profile/1/skills` 

  + 유저의 {idx}의 기술 정보를 가져옴.

  + Success : Code 200

  + Fail : Code 400

  

``` JSON
  {
    "_embedded": {
        "datas": [
            {
                "idx": 48,
                "text": "테스트 스킬 48",
                "_links": {
                    "self": {
                        "href": "https://donghun.dev:8083/api/userskill/48"
                    }
                }
            },
            {
                "idx": 85,
                "text": "테스트 스킬 85",
                "_links": {
                    "self": {
                        "href": "https://donghun.dev:8083/api/userskill/85"
                    }
                }
            }
        ]
    },
    "_links": {
        "self": {
            "href": "https://donghun.dev:8083/api/profile/1/skills"
        }
    }
  }
  ```

* `https://donghun.dev:8083/api/profile/1/projects` 

  + 유저의 {idx}의 진행중인 프로젝트의 정보들을 가져옴.

  + Success : Code 200

  + Fail : Code 400

  

``` JSON
  {
    "_embedded": {
        "datas": [
            {
                "userIdx": 1,
                "projectIdx": 134,
                "leaderNick": "testUser_1",
                "title": "이러 이러한 Side Project 의 함께할 사람들을 찾고 있습니다. 134",
                "status": "진행중",
                "createdDate": "2019-09-06T17:44:03",
                "position": "기타",
                "summary": "이러한 프로젝트에 참여할 인원을 모집합니다."
            },
            {
                "userIdx": 1,
                "projectIdx": 125,
                "leaderNick": "testUser_1",
                "title": "이러 이러한 Side Project 의 함께할 사람들을 찾고 있습니다. 125",
                "status": "진행중",
                "createdDate": "2019-09-06T17:44:03",
                "position": "마케터",
                "summary": "이러한 프로젝트에 참여할 인원을 모집합니다."
            }
        ]
    },
    "_links": {
        "self": {
            "href": "https://donghun.dev:8083/api/profile/1/projects"
        }
    }
  }
  ```

* `https://donghun.dev:8083/api/profile/1/doneprojects` 

  + 유저의 {idx}의 진행했던 프로젝트의 정보들을 가져옴.

  + Success : Code 200

  + Fail : Code 400

  

``` JSON
  {
    "_embedded": {
        "datas": [
            {
                "doneProjectIdx": 77,
                "projectIdx": 50,
                "userIdx": 1,
                "title": "테스트 프로젝트 77",
                "summary": "테스트 프로젝트 77입니다.",
                "content": "테스트 내용 77",
                "createdDate": "2019-09-06T17:44:04",
                "modifiedDate": null,
                "startDate": "2019-09-06T17:44:04",
                "endDate": "2019-09-06T17:44:04",
                "socialUrl": null,
                "usedSkills": [
                    {
                        "idx": 150,
                        "text": "테스트 스킬 150"
                    }
                ],
                "_links": {
                    "self": {
                        "href": "https://donghun.dev:8083/api/doneproject/77"
                    }
                }
            },
            {
                "doneProjectIdx": 103,
                "projectIdx": 41,
                "userIdx": 1,
                "title": "테스트 프로젝트 103",
                "summary": "테스트 프로젝트 103입니다.",
                "content": "테스트 내용 103",
                "createdDate": "2019-09-06T17:44:04",
                "modifiedDate": null,
                "startDate": "2019-09-06T17:44:04",
                "endDate": "2019-09-06T17:44:04",
                "socialUrl": null,
                "usedSkills": [],
                "_links": {
                    "self": {
                        "href": "https://donghun.dev:8083/api/doneproject/103"
                    }
                }
            }
        ]
    },
    "_links": {
        "self": {
            "href": "https://donghun.dev:8083/api/profile/1/doneprojects"
        }
    }
  }
  ```

* `https://donghun.dev:8083/api/comment/1` 

  + 댓글 {idx}의 정보를 가져옴.

  + Success : Code 200

  + Fail : Code 400

  

``` JSON
  {
    "commentIdx": 1,
    "userName": "testUser_24",
    "userIdx": 24,
    "projectTitle": "이러 이러한 Side Project 의 함께할 사람들을 찾고 있습니다. 117",
    "projectIdx": 117,
    "content": "테스트 댓글 1",
    "createdDate": "2019-09-06T17:44:08",
    "modifiedDate": null,
    "_links": {
        "self": {
            "href": "https://donghun.dev:8083/api/comment/1"
        }
    }
  }
  ```

* `https://donghun.dev:8083/api/doneproject/1` 

  + 진행했던 프로젝트 {idx}의 정보를 가져옴.

  + Success : Code 200

  + Fail : Code 400

  

``` JSON
  {
    "commentIdx": 1,
    "userName": "testUser_24",
    "userIdx": 24,
    "projectTitle": "이러 이러한 Side Project 의 함께할 사람들을 찾고 있습니다. 117",
    "projectIdx": 117,
    "content": "테스트 댓글 1",
    "createdDate": "2019-09-06T17:44:08",
    "modifiedDate": null,
    "_links": {
        "self": {
            "href": "https://donghun.dev:8083/api/comment/1"
        }
    }
  }
  ```

* `https://donghun.dev:8083/api/doneproject/1/usedskills` 

  + 진행했던 프로젝트 {idx}의 태그 정보들을 가져옴.

  + Success : Code 200

  + Fail : Code 400

  

``` JSON
  {
    "_embedded": {
        "datas": [
            {
                "idx": 133,
                "text": "테스트 스킬 133",
                "_links": {
                    "self": {
                        "href": "https://donghun.dev:8083/api/usedskill/133"
                    }
                }
            },
            {
                "idx": 152,
                "text": "테스트 스킬 152",
                "_links": {
                    "self": {
                        "href": "https://donghun.dev:8083/api/usedskill/152"
                    }
                }
            }
        ]
    },
    "_links": {
        "self": {
            "href": "https://donghun.dev:8083/api/doneproject/1/usedskills"
        }
    }
  }
  ```

* `https://donghun.dev:8083/api/tag/1` 

  + {idx}의 태그 정보를 가져옴.

  + Success : Code 200

  + Fail : Code 400

  

``` JSON
  {
    "idx": 1,
    "text": "테스트 태그 1",
    "_links": {
        "self": {
            "href": "https://donghun.dev:8083/api/tag/1"
        }
    }
  }
  ```

* `https://donghun.dev:8083/api/userskill/1` 

  + {idx}의 태그 정보를 가져옴.

  + Success : Code 200

  + Fail : Code 400

  

``` JSON
  {
    "idx": 1,
    "text": "테스트 스킬 1",
    "_links": {
        "self": {
            "href": "https://donghun.dev:8083/api/userskill/1"
        }
    }
  }
  ```

* `https://donghun.dev:8083/api/usedskill/1` 

  + {idx}의 태그 정보를 가져옴.

  + Success : Code 200

  + Fail : Code 400

  

``` JSON
  {
    "idx": 1,
    "text": "테스트 스킬 1",
    "_links": {
        "self": {
            "href": "https://donghun.dev:8083/api/usedskill/1"
        }
    }
  }
  ```

* `https://donghun.dev:8083/api/tags` 

  + 모든 태그의 정보들을 가져옴.

  + Success : Code 200

  + Fail : Code 400

  

``` JSON
  {
    "_embedded": {
        "datas": [
            {
                "idx": 1,
                "text": "테스트 태그 1",
                "_links": {
                    "self": {
                        "href": "https://donghun.dev:8083/api/tag/1"
                    }
                }
            },
            {
                "idx": 2,
                "text": "테스트 태그 2",
                "_links": {
                    "self": {
                        "href": "https://donghun.dev:8083/api/tag/2"
                    }
                }
            }
        ]
    },
    "_links": {
        "self": {
            "href": "https://donghun.dev:8083/api/tags"
        }
    }
  }
  ```

* `https://donghun.dev:8083/api/userskills` 

  + {idx}의 태그 정보를 가져옴.

  + Success : Code 200

  + Fail : Code 400

  

``` JSON
  {
    "_embedded": {
        "datas": [
            {
                "idx": 1,
                "text": "테스트 스킬 1",
                "_links": {
                    "self": {
                        "href": "https://donghun.dev:8083/api/userskill/1"
                    }
                }
            },
            {
                "idx": 2,
                "text": "테스트 스킬 2",
                "_links": {
                    "self": {
                        "href": "https://donghun.dev:8083/api/userskill/2"
                    }
                }
            }
        ]
    },
    "_links": {
        "self": {
            "href": "https://donghun.dev:8083/api/userskills"
        }
    }
  }
  ```

* `https://donghun.dev:8083/api/usedskills` 

  + {idx}의 태그 정보를 가져옴.

  + Success : Code 200

  + Fail : Code 400

  

``` JSON
  {
    "_embedded": {
        "datas": [
            {
                "idx": 1,
                "text": "테스트 스킬 1",
                "_links": {
                    "self": {
                        "href": "https://donghun.dev:8083/api/usedskill/1"
                    }
                }
            },
            {
                "idx": 2,
                "text": "테스트 스킬 2",
                "_links": {
                    "self": {
                        "href": "https://donghun.dev:8083/api/usedskill/2"
                    }
                }
            }
        ]
    },
    "_links": {
        "self": {
            "href": "https://donghun.dev:8083/api/usedskills"
        }
    }
  }
  ```

* `https://donghun.dev:8083/api/image/{fileName}` 

  + {fileName}에 해당 이미지를 가져옴

  + Success : Code 200

  + Fail : Code 400

* `https://donghun.dev:8083/api/profile/1/myprojects` 

  + 유저의 {idx}의 개설한 프로젝트의 정보들을 가져옴.

  + Success : Code 200

  + Fail : Code 400

  

``` JSON
  {
    "_embedded": {
        "datas": [
            {
                "userIdx": 1,
                "projectIdx": 134,
                "leaderNick": "testUser_1",
                "title": "이러 이러한 Side Project 의 함께할 사람들을 찾고 있습니다. 134",
                "status": "진행중",
                "createdDate": "2019-09-06T17:44:03",
                "position": "기타",
                "summary": "이러한 프로젝트에 참여할 인원을 모집합니다."
            },
            {
                "userIdx": 1,
                "projectIdx": 125,
                "leaderNick": "testUser_1",
                "title": "이러 이러한 Side Project 의 함께할 사람들을 찾고 있습니다. 125",
                "status": "진행중",
                "createdDate": "2019-09-06T17:44:03",
                "position": "마케터",
                "summary": "이러한 프로젝트에 참여할 인원을 모집합니다."
            }
        ]
    },
    "_links": {
        "self": {
            "href": "https://donghun.dev:8083/api/profile/1/projects"
        }
    }
  }
  ```

* `https://donghun.dev:8083/api/project/1/joinmembers` 

  + 프로젝트 {idx}의 지원자들의 정보를 가져옴.

  + Success : Code 200

  + Fail : Code 400

  

``` JSON
  {
    "_embedded": {
        "datas": [
            {
                "memberIdx": 22,
                "projectIdx": 1,
                "memberNick": "testUser_22",
                "position": "마케터",
                "_links": {
                    "Profile": {
                        "href": "https://donghun.dev:8083/api/profile/22"
                    }
                }
            }
        ]
    },
    "_links": {
        "self": {
            "href": "https://donghun.dev:8083/api/project/1/members"
        }
    }
  }
  ```

* `https://donghun.dev:8083/api/profile/{idx}/applyprojects` 

  + User {idx}의 지원한 프로젝트들의 정보를 가져옴.

  + Success : Code 200

  + Fail : Code 400

  

``` JSON
  {
    "_embedded": {
        "datas": [
            {
                "userIdx": 1,
                "projectIdx": 84,
                "leaderNick": "testUser_3",
                "profileImage": "https://donghun.dev:8083/api/image/USER_DEFAULT_PROFILE_IMG.png",
                "title": "이러 이러한 Side Project 의 함께할 사람들을 찾고 있습니다. 84",
                "status": "모집중",
                "createdDate": "2019-09-19T23:09:27",
                "position": "개발자",
                "summary": "이러한 프로젝트에 참여할 인원을 모집합니다."
            }
        ]
    },
    "_links": {
        "self": {
            "href": "https://donghun.dev:8083/api/profile/1/applyprojects"
        }
    }
    }
  ```

