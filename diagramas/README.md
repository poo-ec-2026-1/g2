@startuml
left to right direction

actor Vendedor

rectangle "Sistema de produtos" {
  usecase "Cadastrar produto" as UC1
  usecase "Cancelar produto" as UC2
  usecase "Editar produto" as UC3
  usecase "Listar produtos" as UC4
}

Vendedor --> UC1
Vendedor --> UC2
Vendedor --> UC3
Vendedor --> UC4
@enduml

![Diagrama de Caso de Uso](//www.plantuml.com/plantuml/png/PP0n2y9034Rt_8hGlODjPoaKDpVHFTp5Ud2zaoITnFzk5KjBkxxt8E5hrKXi7294lXjQHWdzmz07OMSX9m1obWLld3pxB01VGQcFZCKbgF50w1cValreMOiNZCgETD8TUL8JajaMI8hNxh1EakEuAwgbEFbWEryl_XnqvVKVDF06c7_6iZp-pcv0jGNr5ZJGJc2QvmC0)
