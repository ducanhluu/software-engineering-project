`> [1, 0] Program
   +> ListDeclClass [List with 0 elements]
   `> [1, 0] Main
      +> [2, 1] ListDeclVar [List with 1 elements]
      |  []> [2, 5] DeclVar
      |      +> [2, 1] Identifier (int)
      |      |  definition: type (builtin), type=int
      |      +> [2, 5] Identifier (x)
      |      |  definition: variable defined at [2, 5], type=int
      |      `> NoInitialization
      `> [3, 1] ListInst [List with 2 elements]
         []> [3, 1] Assign
         ||  type: int
         ||  +> [3, 1] Identifier (x)
         ||  |  definition: variable defined at [2, 5], type=int
         ||  `> [3, 3] ReadInt
         ||     type: int
         []> [4, 1] Println
             `> [4, 9] ListExpr [List with 1 elements]
                []> [4, 9] Identifier (x)
                    definition: variable defined at [2, 5], type=int
