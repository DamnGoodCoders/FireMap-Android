# FireMap

## Software Engineering Capstone 2019

The 2018 wildfire season was among the most destructive in American history, claiming the lives of 98 civilians and 6 firefighters in California. Colorado faced its own share of fires: over 400 thousand acres were destroyed. Firefighters depend on up-to-date maps that display terrain information and fire boundaries. They are often in areas where cell service is limited, which can delay efforts and risk the lives of both firefighters and civilians.  FireMap addresses this issue by aggregating and rendering these maps on Android devices.  These maps will then be stored on their devices, enabling them to be viewed in remote locations with limited service.  This project will help Colorado firefighters effectively combat wildfires and ultimately help protect the lives and natural resources of the state we call home. 

## Contact Info:
- Caleb Gartner
- Thane Wilson
- Joshua Mathews
-- (225)305-7231
-- jfmathews@mavs.coloradomesa.edu

## Client:
Brad Schmidt 
Fire Technology Specialist 
Center of Excellence for Advanced Technology Aerial Firefighting
brad.schmidt@state.co.us
0375 County Road 352 #2065-A
Rifle, CO 81650
www.dfpc.state.co.us

## Contract Link:
https://github.com/DamnGoodCoders/FireMap-Android/blob/master/Signed%20Contract%20CMU%20DFPC.pdf

## Committing instructions:

All features should have their own branch.  These all should be branched from the Development branch.  

Never merge a feature directly into the Master branch.  First, create a pull request to the Development branch, then we will merge the two.  Only merge the Development branch to the Master branch.

If you're creating a feature, call the branch "feature/feature-name."  Same goes with bugfixes: "bug-fix/issue-name."  Reference the issue you are fixing in your commit.

Once we get the ball rolling, we should require everyone to approve pull requests to the development branch, thoroughly reviewing the changes.

## TODO:

- [x] Compile GDAL as a .aar library
- [x] Create Android project with Google Maps and Navbar (This repository)
- [ ] Overlay GeoTiff image on the map using coordinate metadata
- [ ] Convert GeoSpatialPdf to GeoTiff within application
- [ ] Enable user to change and download maps
