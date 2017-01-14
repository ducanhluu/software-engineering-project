`> [1, 0] Program
   +> ListDeclClass [List with 0 elements]
   `> [1, 0] Main
      +> [2, 1] ListDeclVar [List with 0 elements]
      `> [2, 1] ListInst [List with 1 elements]
         []> [2, 1] IfThenElse
             +> [2, 5] Or
             |  type: boolean
             |  +> [2, 5] Greater
             |  |  type: boolean
             |  |  +> [2, 5] Int (3)
             |  |  |  type: int
             |  |  `> [2, 7] Int (5)
             |  |     type: int
             |  `> [2, 12] Greater
             |     type: boolean
             |     +> [2, 12] Int (2)
             |     |  type: int
             |     `> [2, 14] Int (4)
             |        type: int
             +> [3, 5] ListInst [List with 1 elements]
             |  []> [3, 5] Println
             |      `> [3, 13] ListExpr [List with 1 elements]
             |         []> [3, 13] StringLiteral (or is valid)
             |             type: string
             `> ListInst [List with 0 elements]
