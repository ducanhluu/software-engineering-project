`> [1, 0] Program
   +> ListDeclClass [List with 0 elements]
   `> [1, 0] Main
      +> [2, 0] ListDeclVar [List with 2 elements]
      |  []> [2, 4] DeclVar
      |  ||  +> [2, 0] Identifier (int)
      |  ||  |  definition: type (builtin), type=int
      |  ||  +> [2, 4] Identifier (x)
      |  ||  |  definition: variable defined at [2, 4], type=int
      |  ||  `> [2, 6] Initialization
      |  ||     `> [2, 6] Int (1)
      |  ||        type: int
      |  []> [3, 6] DeclVar
      |      +> [3, 0] Identifier (float)
      |      |  definition: type (builtin), type=float
      |      +> [3, 6] Identifier (y)
      |      |  definition: variable defined at [3, 6], type=float
      |      `> [3, 8] Initialization
      |         `> [3, 8] Float (1.5)
      |            type: float
      `> [4, 0] ListInst [List with 2 elements]
         []> [4, 0] Assign
         ||  type: float
         ||  +> [4, 0] Identifier (y)
         ||  |  definition: variable defined at [3, 6], type=float
         ||  `> [4, 2] Plus
         ||     type: float
         ||     +> ConvFloat
         ||     |  type: float
         ||     |  `> [4, 2] Identifier (x)
         ||     |     definition: variable defined at [2, 4], type=int
         ||     `> [4, 4] Float (3.2)
         ||        type: float
         []> [5, 0] Print
             `> [5, 6] ListExpr [List with 1 elements]
                []> [5, 6] Identifier (y)
                    definition: variable defined at [3, 6], type=float
