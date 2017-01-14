`> [1, 0] Program
   +> ListDeclClass [List with 0 elements]
   `> [1, 0] Main
      +> [2, 0] ListDeclVar [List with 2 elements]
      |  []> [2, 4] DeclVar
      |  ||  +> [2, 0] Identifier (int)
      |  ||  |  definition: type (builtin), type=int
      |  ||  +> [2, 4] Identifier (x)
      |  ||  |  definition: variable defined at [2, 4], type=int
      |  ||  `> NoInitialization
      |  []> [3, 6] DeclVar
      |      +> [3, 0] Identifier (float)
      |      |  definition: type (builtin), type=float
      |      +> [3, 6] Identifier (y)
      |      |  definition: variable defined at [3, 6], type=float
      |      `> [3, 8] Initialization
      |         `> [3, 8] Float (2.3)
      |            type: float
      `> [4, 0] ListInst [List with 2 elements]
         []> [4, 0] Assign
         ||  type: float
         ||  +> [4, 0] Identifier (y)
         ||  |  definition: variable defined at [3, 6], type=float
         ||  `> [4, 2] Plus
         ||     type: float
         ||     +> [4, 4] Plus
         ||     |  type: float
         ||     |  +> [4, 2] Identifier (y)
         ||     |  |  definition: variable defined at [3, 6], type=float
         ||     |  `> ConvFloat
         ||     |     type: float
         ||     |     `> [4, 2] Identifier (y)
         ||     |        definition: variable defined at [3, 6], type=float
         ||     `> [4, 6] Identifier (y)
         ||        definition: variable defined at [3, 6], type=float
         []> [5, 0] Print
             `> [5, 6] ListExpr [List with 1 elements]
                []> [5, 6] Identifier (y)
                    definition: variable defined at [3, 6], type=float
