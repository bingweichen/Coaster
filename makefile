JC = javac

SRCDIR = src/
DISPLAYDIR = $(SRCDIR)display/

.SUFFIXES: .java .class
.java.class:
		$(JC) $(SRCDIR)*.java $(DISPLAYDIR)*.java

CLASSES = \
		$(SRCDIR)CoasterCar.java \
		$(SRCDIR)Controller.java \
		$(SRCDIR)Passengers.java \
		$(SRCDIR)RollerCoaster.java \
		$(SRCDIR)PlatformAccess.java \
		$(SRCDIR)RollerCoasterFrame.java \
		$(SRCDIR)PlatformDepartureException.java \
		$(DISPLAYDIR)NumberCanvas.java \
		$(DISPLAYDIR)ThreadPanel.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
		$(RM) *.class
