# BankApi
Por lo que he testeado en PostMan la Api funciona correctamente 
pero hay pocos tests hechos porque me he estado peleando con 
un bucle infinito de llamadas a metodos que provocaba el 
gson.toJason que al final he podido arreglar con la clase 
MyTypeAdapter que extiende la clase TypeAdapter haciendo
que los valores null no den problemas.
Los tests de creacion de usuarios funcionan correctamente pero
los test de creacion de cuentas me dan un problema por que debería 
mockear el guardado de los accountholders en el AccountholderRepository
entiendo. Tengo bastastes dudas acerca de los tests que me gustaria 
poder revisar el lunes o martes aunque sea fuera de tiempo de entrega
para poder aprender sobre ello.

*-->Ignorar si se quiere los comentarios en las paginas de los tests,
eran apuntes sobretodo para mi para ir sabiendo lo que habia 
probado y lo que no y para vosotros por si lo entregaba a ultima hora sin poder
hacer el readme*

