# CallCenter
Ejercicio Técnico para [almundo](https://almundo.com/).
<br/><br/>

## Introducción

Este es un proyecto que simula un call center. El sistema puede ser visto como un productor-consumidor con un buffer intermedio. Tiene las siguientes partes:
<br/><br/>

1) Un Productor que genera llamadas y las deposita en una cola de llamadas.
2) La cola de llamadas.
3) Un conjunto de empleados que atienden llamadas.


Sin embargo, los empleados no toman autónomamente las llamadas de la cola: Existe un Dispatcher, encargado de asignar las llamadas a los empleados.
<br/><br/>

A continuación, analizaremos cada módulo.
<br/><br/>

## Productor

El [productor](https://github.com/gmazzei/CallCenter/blob/master/src/main/java/com/almundo/model/Producer.java) es uno solo, y ejecuta en su propio hilo al iniciar el call-center. Repite constantemente estos pasos:
<br/><br/>

1) Genera una llamada.
2) Deposita la llamada en la cola de llamadas.
3) Espera un tiempo aleatorio entre 1 y 3 segundos (para simular un call center real).
<br/>

## Cola de llamadas

La [cola de llamadas](https://github.com/gmazzei/CallCenter/blob/master/src/main/java/com/almundo/app/App.java#L16) puede almacenar hasta 10 llamadas. Luego, si se intenta agregar una nueva llamada, la misma será rechazada y descartada. Este es un caso excepcional. Si fuera un call center real, tendríamos una grabación que le diría al usuario "Todas nuestras operadoras están ocupadas. Por favor, intente más tarde". Es decir, están todos ocupados y, además, no podemos dejar su llamada en espera porque la cola está llena.
<br/><br/>

## Dispatcher

El [Dispatcher](https://github.com/gmazzei/CallCenter/blob/master/src/main/java/com/almundo/model/Dispatcher.java) es uno solo, y ejecuta en su propio hilo al iniciar el call-center. <br/>
Contiene tres colas: operadores, supervisores y directores. Sus tamaño son de 6, 3 y 1 respectivamente (y pueden modificarse).<br/>
Además, el dispatcher crea [10 hilos](https://github.com/gmazzei/CallCenter/blob/master/src/main/java/com/almundo/model/Dispatcher.java#L161) que escuchan si llega una llamada a la cola. Si esto sucede, se toma un empleado de la cola, se toma una llamada y se responde la misma. La reserva de un empleado y una llamada tiene que ser ejecutada [atómicamente](https://github.com/gmazzei/CallCenter/blob/master/src/main/java/com/almundo/model/Dispatcher.java#L101), ya que sino podría reservarse un empleado y tener que encolarlo nuevamente porque otro hilo le quitó la llamada. <br/>
¿Qué pasa si hay llamada pero no hay empleado? Simplemente se deja la llamada en la cola de espera hasta que haya algún empleado disponible.<br/>
El Dispatcher también actualiza un [contador de hilos ocupados](https://github.com/gmazzei/CallCenter/blob/master/src/main/java/com/almundo/model/Dispatcher.java#L25), para saber cuántos de ellos están gestionando una llamada.
<br/><br/>

## Llamadas

Cada [llamada](https://github.com/gmazzei/CallCenter/blob/master/src/main/java/com/almundo/model/Call.java) tiene un ID para ser identificada y un tiempo autogenerado entre 5 y 10 segundos.
<br/><br/>

## Empleados

Todos los [empleados](https://github.com/gmazzei/CallCenter/blob/master/src/main/java/com/almundo/model/Employee.java) tienen el mismo comportamiento sin importar su puesto. Es decir, todos atienden llamadas. Quien los diferencia es el Dispatcher al momento de enviarles llamadas.<br/>
Cada empleado tiene un ID, un puesto y conoce su cola, para que el dispatcher no tenga que analizar qué puesto tiene cuando haya que enviarlo de vuelta a su cola (polimorfismo en vez de ifs).<br/>
Para modelar a los empleados, se eligió composición en vez de herencia. A pesar de que esta última parecía más natural, no otorgaba mucha utilidad al momento de codificar el Dispatcher. Como todos los empleados son iguales, la opción más simple es que el puesto sea un atributo del empleado. Este atributo es un objeto de clase [Puesto](https://github.com/gmazzei/CallCenter/blob/master/src/main/java/com/almundo/model/Position.java) y tiene comportamiento propio. Esto podría verse parecido un patrón Strategy.<br/>
<br/>

## Decisiones de diseño

Al tomar decisiones de diseño y arquitectura, se pensó en estos principios:
1) Prevenir condiciones de carrera.
2) Prevenir Deadlocks. 
3) Administrar con justicia los hilos y pedidos (ej: prevenir starvation).
4) Escribir código expresivo y respetar las buenas prácticas.
<br/>

### BlockingQueue

Se eligió la [BlockingQueue](https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/BlockingQueue.html) por ser thread safe, organizar de forma justa las llamadas (FIFO) y elegir un empleado de forma justa (la colas de operadores, supervisores y directores se administran cada una con FIFO).
