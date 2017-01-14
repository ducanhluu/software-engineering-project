`> [1, 0] Program
   +> ListDeclClass [List with 0 elements]
   `> [1, 0] Main
      +> [2, 0] ListDeclVar [List with 0 elements]
      `> [2, 0] ListInst [List with 1 elements]
         []> [3, 7] While
             +> [2, 8] Greater
             |  type: boolean
             |  +> [2, 8] Int (1)
             |  |  type: int
             |  `> [2, 12] Int (2)
             |     type: int
             `> [3, 7] ListInst [List with 1 elements]
                []> [3, 7] Println
                    `> [3, 15] ListExpr [List with 1 elements]
                       []> [3, 15] StringLiteral (Hello)
                           type: string
