`> [1, 0] Program
   +> ListDeclClass [List with 0 elements]
   `> [1, 0] Main
      +> [2, 0] ListDeclVar [List with 0 elements]
      `> [2, 0] ListInst [List with 1 elements]
         []> [2, 0] IfThenElse
             +> [2, 5] Lower
             |  type: boolean
             |  +> [2, 5] Int (2)
             |  |  type: int
             |  `> [2, 9] Int (5)
             |     type: int
             +> [3, 3] ListInst [List with 1 elements]
             |  []> [3, 3] Println
             |      `> [3, 11] ListExpr [List with 1 elements]
             |         []> [3, 11] StringLiteral (hello)
             |             type: string
             `> ListInst [List with 1 elements]
                []> IfThenElse
                    +> [4, 12] Greater
                    |  type: boolean
                    |  +> [4, 12] Int (3)
                    |  |  type: int
                    |  `> [4, 16] Int (88)
                    |     type: int
                    +> [5, 2] ListInst [List with 1 elements]
                    |  []> [5, 2] Println
                    |      `> [5, 10] ListExpr [List with 1 elements]
                    |         []> [5, 10] StringLiteral (bye)
                    |             type: string
                    `> ListInst [List with 1 elements]
                       []> IfThenElse
                           +> [6, 12] Equals
                           |  type: boolean
                           |  +> [6, 12] Int (1)
                           |  |  type: int
                           |  `> [6, 17] Int (1)
                           |     type: int
                           +> [7, 2] ListInst [List with 1 elements]
                           |  []> [7, 2] Println
                           |      `> [7, 10] ListExpr [List with 1 elements]
                           |         []> [7, 10] StringLiteral (helloagain)
                           |             type: string
                           `> ListInst [List with 1 elements]
                              []> [9, 2] Println
                                  `> [9, 10] ListExpr [List with 1 elements]
                                     []> [9, 10] StringLiteral (farewell)
                                         type: string
