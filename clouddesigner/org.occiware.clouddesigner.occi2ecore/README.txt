Dans l'ordre d'utilisation :
- FixAllDynamics copie les fichiers issus du projet metamodels/ et au passage change les href vers le fichier OCCI.ecore local vers le m�tamod�le OCCI d�ploy�
- ConvertInfrastructure convertit l'extension Infrastructure.xmi en m�tamod�le Ecore �tendant le m�tamod�le OCCI
- ConvertDocker convertit l'extension Docker.xmi en m�tamod�le Ecore �tendant le m�tamod�le OCCI et Infrastructure
- ConvertDockerConfiguration convertit les configurations Docker en configurations Docker exploitant le m�tamod�le Docker au lieu des kinds

! Attention toutes les �tapes ne sont pas finalis�es, il faut encore retoucher le produit de certaines transformations avant l'�tape suivante.

Les fichiers CHANGES indiquent les changements faits ou � faire sur les fichiers avant utilisation.

