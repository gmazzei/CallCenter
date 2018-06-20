# CallCenter
Ejercicio Técnico para almundo
<br/><br/>

## Introducción

Este es un proyecto que simula un call center. El sistema puede ser visto como un Productor-Consumidor con un buffer intermedio. Tiene las siguientes partes:
<br/><br/>

1) Un Productor que genera llamadas y las deposita en una cola de llamadas.
2) La cola de llamadas.
3) Un conjunto de empleados que atienden llamadas.


Sin embargo, los empleados no toman autónomamente las llamadas de la cola: Existe un Dispatcher, encargado de asignar las llamadas a los empleados.
<br/><br/>

A continuación, analizaremos cada módulo.
<br/><br/>

## Productor

El productor es uno solo, y ejecuta en su propio hilo al iniciar el call-center. Repite constantemente estos pasos:
<br/><br/>

1) Genera una llamada.
2) Deposita la llamada en la cola de llamadas.
3) Espera un tiempo aleatorio entre 1 y 3 segundos (para simular un call center real).
<br/>

## Cola de llamadas

La cola de llamadas puede almacenar hasta 10 llamadas. Luego, si se intenta agregar una nueva llamada, la misma será rechazada y descartada. Este es un caso excepcional. Si fuera un call center real, tendríamos una grabación que le diría al usuario "Todas nuestras operadoras están ocupadas. Por favor, intente más tarde". Es decir, están todos ocupados y, además, no podemos dejar su llamada en espera porque la cola está llena.
<br/><br/>

## Dispatcher


<br/><br/>

## Llamadas

Cada llamada tiene un ID para ser identificada y un tiempo autogenerado entre 5 y 10 segundos.
<br/><br/>

## Empleados

Todos los empleados tienen el mismo comportamiento sin importar su puesto. Es decir, todos atienden llamadas. Quien los diferencia es el Dispatcher al momento de enviarles llamadas.<br/>
Cada empleado tiene un ID, un puesto y conoce su cola, para que el dispatcher no tenga que analizar qué puesto tiene cuando haya que enviarlo de vuelta a su cola (polimorfismo en vez de ifs).<br/>
Para modelar a los empleados, se eligió composición en vez de herencia. A pesar de que esta última parecía más natural, no representaba mucha utilidad al momento de códificar el Dispatcher. Como todos los empleados son iguales, la opción más simple es que el puesto sea sólo un String o bien, un objeto que de interfaz "Puesto", por si en el futuro se quiere agregar más funcionalidad relativa al mismo. Esto puede verse como un patrón Strategy, si se le agregara más funcionalidad.

<br/><br/>
