# AI Learns Flappy Bird

This project demonstrates how an AI can learn to play **Flappy Bird** using the **N.E.A.T.** (NeuroEvolution of Augmenting Topologies) algorithm. N.E.A.T. is a genetic algorithm that trains neural networks by evolving them through generations of agents in a simulated environment.

## Overview

Each bird in the simulation is controlled by a **Neural Network** that decides whether to flap based on its inputs. Birds with the best-performing neural networks are selected for the next generation, and small mutations are applied to their networks to introduce new behavior. This process continues across generations until the birds can successfully navigate through the environment.

## How It Works

### 1. Initial Population
- The simulation starts by creating an initial population of `N` birds, each with a randomly initialized neural network.

### 2. Simulation
- Each bird interacts with the environment (Flappy Bird game) and decides when to flap based on its neural network's output.
- The birds continue until all of them crash (die).

### 3. Selection & Mutation
- The neural networks of the best-performing birds are selected for the next generation.
- Small mutations (e.g., random changes to 5% of the weights) are applied to these networks to encourage exploration of new behaviors.

### 4. Evolution
- The process is repeated for many generations. Over time, the birds improve at navigating the environment as successful neural network configurations are refined.

## Development Process

I began by developing my own **neural network program**, which took about 1.5 months to complete. This implementation was a **Topology and Weight Evolving Artificial Neural Network (TWEANN)**, which laid the foundation for applying NEAT effectively.

### Challenges
- Implementing NEAT posed significant challenges, as there were limited resources detailing the specifics of the algorithm. Much of the work involved tuning the parameters, which took another month.
- The entire project, including the TWEANN, NEAT algorithm, and the Flappy Bird simulator, took roughly **3 months** to complete.

## Training Process

1. **Create Population:** Initialize a population of birds with random neural networks.
2. **Simulate Environment:** Let the birds interact with the environment until they all die.
3. **Selection:** Identify the best-performing birds and use their neural networks as a base for the next generation.
4. **Mutation:** Randomly modify a small portion of the neural networks' weights (e.g., 5%) to generate new behaviors.
5. **Repeat:** Continue this process over many generations until the birds learn to navigate the environment successfully.

## Technologies Used

- **Java**: The primary programming language used for building the AI and game simulation.
- **JFrame**: Utilized for rendering the Flappy Bird game environment and handling the graphical user interface.
- **Custom NEAT Implementation**: Implemented my own original version of NEAT (NeuroEvolution of Augmenting Topologies) algorithm to evolve neural networks
- **TWEANN**: Implemented a custom Topology and Weight Evolving Artificial Neural Network (TWEANN) to allow for dynamic neural network topology changes during training.

## Acknowledgments

This project was heavily influenced by research and resources that helped guide my understanding and implementation of NEAT:

- **Stanley, Kenneth O., and Risto Miikkulainen.** "Evolving Neural Networks through Augmenting Topologies." *MIT Press* (2002). [Read the paper here](https://nn.cs.utexas.edu/downloads/papers/stanley.ec02.pdf).
- Various YouTube videos provided visual examples and explanations of NEAT and its applications:
    - [AI NEAT (PlayList) - Finn Eggers ](https://www.youtube.com/watch?v=VMQOa4-rVxE&list=PLgomWLYGNl1fcL0o4exBShNeCC5tc6s9C&ab_channel=FinnEggers)
    - [NEAT Algorithm Visually Explained - David Schäfer](https://youtu.be/yVtdp1kF0I4?si=SfH0ouURtsrmutv2)
    - [Neuroevolution of Augmenting Topologies (NEAT) - Connor Shorten](https://youtu.be/b3D8jPmcw-g?si=cYXFsVGeuDNijyFI)
    - [Snake learns with NEUROEVOLUTION (implementing NEAT from scratch in C++) - Tech with Nikola](https://youtu.be/lAjcH-hCusg?si=KqwSVm_Ezv7lWrSt)
- **Inspiration**: The "Neat AI does Flappy Birds using NEAT and a Genetic Algorithm" project also served as a source of inspiration for my work. [Watch it here](https://youtu.be/ihX3-WDua2I?si=c3H7H50OZySyGrbp).

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
