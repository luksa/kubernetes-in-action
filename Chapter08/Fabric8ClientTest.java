package kubia;

import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.api.model.PodList;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;

import java.util.Arrays;

public class Fabric8ClientTest {

    public static void main(String[] args) throws Exception {
        KubernetesClient client = new DefaultKubernetesClient();
        
        // list pods in the default namespace
        PodList pods = client.pods().inNamespace("default").list();
        pods.getItems().stream().forEach(s -> System.out.println("Found pod: " + s.getMetadata().getName()));
        
        // create a pod
        System.out.println("Creating a pod");
        Pod pod = client.pods().inNamespace("default").createNew()
                .withNewMetadata()
                    .withName("my-programmatically-created-pod")
                .endMetadata()
                .withNewSpec()
                    .addNewContainer()
                        .withName("main")
                        .withImage("busybox")
                        .withCommand(Arrays.asList("sleep", "99999"))
                    .endContainer()
                .endSpec()
                .done();
        System.out.println("Created pod: " + pod);

        // edit the pod (add a label to it)
        client.pods().inNamespace("default").withName("my-programmatically-created-pod").edit()
                .editMetadata()
                    .addToLabels("foo", "bar")
                .endMetadata()
                .done();
        System.out.println("Added label foo=bar to pod");

        System.out.println("Waiting 1 minute before deleting pod...");
        Thread.sleep(60000);

        // delete the pod
        client.pods().inNamespace("default").withName("my-programmatically-created-pod").delete();
        System.out.println("Deleted the pod");
    }
}
