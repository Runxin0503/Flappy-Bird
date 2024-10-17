import Evolution.*;
import Genome.Genome;
import Render.Frame;
import globalGenomes.globalInnovations;
import globalGenomes.globalNodes;

public class main {
    public static void main(String[] args){
        globalNodes globalNodes = new globalNodes(4,1);
        globalInnovations globalInnovations = new globalInnovations(globalNodes);
        Evolution evolution = new Evolution(0);
        NN test = new NN(new Genome(globalInnovations,globalNodes));
        System.out.println(test.genome);
        new Frame(test);
    }
    // public static void main(String[] args) throws Exception {
    //     ArrayList<innovation> globalInnovationBank = new ArrayList<innovation>();
    //     Random rand = new Random();

    //     while (true){
    //         NN testSubject = new NN(10,2,"sigmoid","softmax");
    //         int mutationValue = 5;
    //         // System.out.println("Mutating");
    //         for(int i=0;i<100;i++){
    //             testSubject.mutate(globalInnovationBank,mutationValue);
    //         }
    //         double[] testInput = new double[10];
    //         for(int i=0;i<10;i++){
    //             testInput[i] = rand.nextGaussian(0,100);
    //         }
    //         // System.out.println("Beginning calculation");
    //         double[] output  = testSubject.calculateWeightedOutput(testInput);
    //         System.out.print(output[0] + " : " + output[1]);
    //         System.out.println((output[0]==output[1] && output[0]==0)?" ------------------------------------- FAIL":"");
    //         // System.out.println("Running it back...\n");
    //     }
    // }
}
