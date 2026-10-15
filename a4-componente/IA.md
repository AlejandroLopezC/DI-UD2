# Registro de uso de Inteligencia Artificial (IA)

## 1. Uso de la IA

Se ha utilizado ChatGPT como herramienta de apoyo durante el desarrollo del componente reutilizable `CampoValidado` y su integración en la aplicación JavaFX del proyecto A3.

La IA se ha empleado para resolver dudas, revisar errores de compilación, mejorar la validación del campo de texto y comprobar la integración entre los dos proyectos Maven.

## 2. Prompts utilizados

- Ayúdame a desarrollar un componente JavaFX reutilizable para validar un campo de texto.
- Explícame cómo implementar propiedades observables, validación y eventos en JavaFX.
- Ayúdame a integrar el componente A4 en la pantalla principal del proyecto A3.
- Analiza y ayúdame a solucionar los errores de compilación relacionados con los módulos Java y los paquetes.
- Ayúdame a comprobar que el botón de validación muestra correctamente el resultado.

## 3. Correcciones realizadas con ayuda de la IA

Durante el desarrollo se realizaron las siguientes correcciones:

1. Se cambió el paquete de `CampoValidado` a `es.dam.di.componente` para evitar conflictos entre módulos.
2. Se eliminó la clase de inicio `App.java` del proyecto A4, ya que provocaba un conflicto de paquetes al utilizarlo como dependencia.
3. Se actualizó el import del componente en A3 y se añadió la dependencia modular correspondiente.
4. Se mejoró la interfaz de A3 para mostrar el resultado de la validación del nombre.
5. Se corrigieron errores de sintaxis y se comprobó el funcionamiento de la aplicación.

## 4. Comprobaciones

Se comprobó la compilación e instalación del componente A4 mediante Maven. También se verificó su integración en A3 y se realizaron pruebas de validación con nombres de distinta longitud.

## 5. Valoración personal

La IA ha servido como apoyo para comprender los errores y encontrar soluciones. Las correcciones se han aplicado y comprobado en el entorno de desarrollo, lo que ha permitido entender mejor la reutilización de componentes JavaFX, Maven y el sistema de módulos de Java.