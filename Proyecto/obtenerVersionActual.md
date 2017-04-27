Cómo obtener una versión del sistema
===
Image Preprocessing (CopyLeft) 2017

## Descripción
El sistema de Image Preprocessing se encuentra en un estado de constante desarrollo. Sin embargo, en determinado momento se puede marcar un estado del sistema que sea estable y cuente con una serie de funcionalidades con resultados esperados. A esto se le llama versionamiento del sistema. Normalmente se establece por medio de una etiqueta y una numeración semántica que permite identificar el estado del sistema en la versión que se encuentre.

## Dependencias
1. git
0. Inclusión de la ruta de git al PATH del sistema.

## Pasos
Para obtener una versión específica del sistema, se debe seguir una serie de pasos que se enumeran a continuación:

1. Ingrese a una terminal en su sistema operativo de preferencia y navegue hasta la ruta del git en su sistema:
```
developer:~/Documentos/ImagePreprocessing/$ _
```
Puede utilizar ```cd``` o ```dir``` para la navegación, según utilice linux o windows.

0. Compruebe el estado actual de su versión ejecutando el comando ```git status``` y revisando el resultado:
```
developer:~/$ git status
On branch master
Your branch is ahead of 'origin/master' by 2 commits.
  (use "git push" to publish your local commits)
...
...
```
Si este comando retorna un resultado similar a este, git y su repositorio se mantienen en orden. Verifique que no contenga archivos modificados sin actualizar.

0. Ejecute el comando ```git tag``` para verificar las versiones existentes en el sistema:
```
developer:~/$ git tag
v0.1
v1.3
v1.4
v1.4-lw
v1.5
```

0. Según la intención de la recuperación, hay distintas maneras de ejecutar la tarea.

    1. Si se planea desarrollar sobre tal versión o "editarla", se debe crear una nueva rama de git y clonar su contenido a la misma para trabajar sobre la versión con el siguiente comando:
    ```
    developer:~/$ git checkout -B nombreRama v2.0.0
    ```
    0. Si se desea recuperar la versión indicada para algún tipo de ejecución, se puede hacer con:
    ```
    developer:~/$ git checkout v2.0.0
    ```
    Sim embargo, luego se trabajar o copiar el sistema, se debe reestablecer el puntero al último commit, pues esta operación deja al repositorio en un estado de desenlace (detached). Esto se puede arreglar por medio del comando:
    ```
    developer:~/$ git checkout HEAD
    ```

### Referencias
https://git-scm.com/book/en/v2/Git-Basics-Tagging
