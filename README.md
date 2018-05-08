TicTacToe

Author: Firoz

Steps to Deploy

    1. Clone the repo
    2. cd to project folder : TicTacToe
    3. Build the application by running : mvn clean install

Start minikube
switch the docker instance inside minikube by running : minikube docker-env | Invoke-Expression (in powershell) or eval $(minikube docker-env) (in linux shell)

    3. Create the game image by running : docker build -t fx/tictactoe:v1 .
    4. Create the kubernetes namespace by running : kubectl.exe create -f .\k8s_deployments\fx_ns.yaml
    5. Create the mongo DB deployment by running : kubectl.exe create -f .\k8s_deployments\mongo-deployment.yaml
    6. Create the mongo DB service by running : kubectl.exe create -f .\k8s_deployments\mongo-service.yaml
    7. Create the game deployment by running : kubectl.exe create -f .\k8s_deployments\game-deployment.yaml
    8. Create the game service by running : kubectl.exe create -f .\k8s_deployments\game-service.yaml
    9. Get the game service url by running : minikube.exe --namespace fx service tictactoe --url

Open the REST client of your choice and use the following URLs to play the game:

Create a new game:

    URL: http://{host:port}/game
    METHOD: POST
    BODY: (form-data): 
        input={"name"="your_name","character":"O"}
    
    expected output: 
        Created new game with ID:x

View game with ID x:

    URL: http://{host:port}/game/x
    METHOD: GET
        
    expected output: 
          A B C
        A| | | |
        B| | | |
        C| | | |

        Game ongoing, your turn ...

Make a move in game with ID x to coordinates row=A,column=B:

    URL: http://{host:port}/game/x/move
    METHOD: POST
    BODY: (form-data): 
        input={"row"="A","column":"B"}
        
    expected output: 
          A B C
        A| |O| |
        B| | | |
        C|X| | |

        Game ongoing, your turn ...

Used jMeter to perform performance testing, test plan is attached in repo.