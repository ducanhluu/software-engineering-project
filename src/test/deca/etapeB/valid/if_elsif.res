`> [1, 0] Program
   +> ListDeclClass [List with 0 elements]
   `> [1, 0] Main
      +> [2, 0] ListDeclVar [List with 0 elements]
      `> [2, 0] ListInst [List with 1 elements]
         []> [2, 0] IfThenElse
             +> [2, 3] Equals
             |  type: boolean
             |  +> [2, 3] Int (1)
             |  |  type: int
             |  `> [2, 6] Int (2)
             |     type: int
             +> [3, 0] ListInst [List with 1 elements]
             |  []> [3, 0] Print
             |      `> [3, 6] ListExpr [List with 1 elements]
             |         []> [3, 6] StringLiteral (hello)
             |             type: string
             `> ListInst [List with 1 elements]
                []> IfThenElse
                    +> [4, 10] Equals
                    |  type: boolean
                    |  +> [4, 10] Int (1)
                    |  |  type: int
                    |  `> [4, 13] Int (3)
                    |     type: int
                    +> [5, 0] ListInst [List with 1 elements]
                    |  []> [5, 0] Print
                    |      `> [5, 6] ListExpr [List with 1 elements]
                    |         []> [5, 6] StringLiteral (bye)
                    |             type: string
                    `> ListInst [List with 0 elements]
