`> [6, 0] Program
   +> ListDeclClass [List with 0 elements]
   `> [6, 0] Main
      +> [7, 0] ListDeclVar [List with 0 elements]
      `> [7, 0] ListInst [List with 1 elements]
         []> [7, 0] IfThenElse
             +> [7, 5] Lower
             |  type: boolean
             |  +> ConvFloat
             |  |  `> [7, 5] Int (3)
             |  |     type: int
             |  `> [7, 8] Float (2.3)
             |     type: float
             +> [8, 3] ListInst [List with 1 elements]
             |  []> [8, 3] Println
             |      `> [8, 11] ListExpr [List with 1 elements]
             |         []> [8, 11] StringLiteral (this shouldn't be printed)
             |             type: string
             `> ListInst [List with 0 elements]
