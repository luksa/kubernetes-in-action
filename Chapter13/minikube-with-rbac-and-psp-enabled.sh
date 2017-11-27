#!/bin/bash

# The following command works with minikube 0.19.0 and 0.19.1, but may not work with later versions (see command below)
minikube start --extra-config apiserver.Authentication.PasswordFile.BasicAuthFile=/etc/kubernetes/passwd --extra-config=apiserver.Authorization.Mode=RBAC --extra-config=apiserver.GenericServerRunOptions.AdmissionControl=NamespaceLifecycle,LimitRanger,ServiceAccount,PersistentVolumeLabel,DefaultStorageClass,PodSecurityPolicy,ResourceQuota,DefaultTolerationSeconds

# The following command might work with newer versions of minikube
#minikube start --extra-config apiserver.Authentication.PasswordFile.BasicAuthFile=/etc/kubernetes/passwd --extra-config=apiserver.Authorization.Mode=RBAC --extra-config=apiserver.Admission.PluginNames=NamespaceLifecycle,LimitRanger,ServiceAccount,PersistentVolumeLabel,DefaultStorageClass,PodSecurityPolicy,ResourceQuota,DefaultTolerationSeconds

cat <<EOF | minikube ssh sudo tee /etc/kubernetes/passwd
password,alice,1000,basic-user
password,bob,2000,privileged-user
EOF

