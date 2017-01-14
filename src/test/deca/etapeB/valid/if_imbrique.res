`> [1, 0] Program
   +> ListDeclClass [List with 0 elements]
   `> [1, 0] Main
      +> [2, 0] ListDeclVar [List with 0 elements]
      `> [2, 0] ListInst [List with 1 elements]
         []> [2, 0] IfThenElse
             +> [2, 5] Greater
             |  type: boolean
             |  +> [2, 5] Int (2)
             |  |  type: int
             |  `> [2, 9] Int (4)
             |     type: int
             +> [3, 3] ListInst [List with 1 elements]
             |  []> [3, 3] IfThenElse
             |      +> [3, 8] Greater
             |      |  type: boolean
             |      |  +> [3, 8] Int (2)
             |      |  |  type: int
             |      |  `> [3, 12] Int (3)
             |      |     type: int
             |      +> [4, 6] ListInst [List with 1 elements]
             |      |  []> [4, 6] Print
             |      |      `> [4, 12] ListExpr [List with 1 elements]
             |      |         []> [4, 12] StringLiteral (boucle imbriquée : OK)
             |      |             type: string
             |      `> ListInst [List with 0 elements]
             `> ListInst [List with 0 elements]
