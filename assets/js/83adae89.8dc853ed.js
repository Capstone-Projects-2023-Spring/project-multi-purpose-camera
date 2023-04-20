"use strict";(self.webpackChunkcreate_project_docs=self.webpackChunkcreate_project_docs||[]).push([[3211],{3905:(e,r,t)=>{t.d(r,{Zo:()=>c,kt:()=>h});var a=t(7294);function n(e,r,t){return r in e?Object.defineProperty(e,r,{value:t,enumerable:!0,configurable:!0,writable:!0}):e[r]=t,e}function i(e,r){var t=Object.keys(e);if(Object.getOwnPropertySymbols){var a=Object.getOwnPropertySymbols(e);r&&(a=a.filter((function(r){return Object.getOwnPropertyDescriptor(e,r).enumerable}))),t.push.apply(t,a)}return t}function o(e){for(var r=1;r<arguments.length;r++){var t=null!=arguments[r]?arguments[r]:{};r%2?i(Object(t),!0).forEach((function(r){n(e,r,t[r])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(t)):i(Object(t)).forEach((function(r){Object.defineProperty(e,r,Object.getOwnPropertyDescriptor(t,r))}))}return e}function s(e,r){if(null==e)return{};var t,a,n=function(e,r){if(null==e)return{};var t,a,n={},i=Object.keys(e);for(a=0;a<i.length;a++)t=i[a],r.indexOf(t)>=0||(n[t]=e[t]);return n}(e,r);if(Object.getOwnPropertySymbols){var i=Object.getOwnPropertySymbols(e);for(a=0;a<i.length;a++)t=i[a],r.indexOf(t)>=0||Object.prototype.propertyIsEnumerable.call(e,t)&&(n[t]=e[t])}return n}var u=a.createContext({}),l=function(e){var r=a.useContext(u),t=r;return e&&(t="function"==typeof e?e(r):o(o({},r),e)),t},c=function(e){var r=l(e.components);return a.createElement(u.Provider,{value:r},e.children)},d="mdxType",p={inlineCode:"code",wrapper:function(e){var r=e.children;return a.createElement(a.Fragment,{},r)}},m=a.forwardRef((function(e,r){var t=e.components,n=e.mdxType,i=e.originalType,u=e.parentName,c=s(e,["components","mdxType","originalType","parentName"]),d=l(t),m=n,h=d["".concat(u,".").concat(m)]||d[m]||p[m]||i;return t?a.createElement(h,o(o({ref:r},c),{},{components:t})):a.createElement(h,o({ref:r},c))}));function h(e,r){var t=arguments,n=r&&r.mdxType;if("string"==typeof e||n){var i=t.length,o=new Array(i);o[0]=m;var s={};for(var u in r)hasOwnProperty.call(r,u)&&(s[u]=r[u]);s.originalType=e,s[d]="string"==typeof e?e:n,o[1]=s;for(var l=2;l<i;l++)o[l]=t[l];return a.createElement.apply(null,o)}return a.createElement.apply(null,t)}m.displayName="MDXCreateElement"},9711:(e,r,t)=>{t.r(r),t.d(r,{assets:()=>u,contentTitle:()=>o,default:()=>d,frontMatter:()=>i,metadata:()=>s,toc:()=>l});var a=t(7462),n=(t(7294),t(3905));const i={sidebar_position:3},o="General Requirements",s={unversionedId:"requirements/general-requirements",id:"requirements/general-requirements",title:"General Requirements",description:"The general requirements for this project corresponds with the hardware aspect and the software aspect. The hardwware requires a Raspberry Pi and the additional hardware accessories (such as the camera, mic, SD card, etc). With working with the Pi, there also needs to be a basic knowledge of what a Pi is, the capabilities, and how it functions. The software required is the Java Development Kit and an IDE capable of syncing to GitHub. For this, Android Studios is used, which conveniently has built in JDKs and emulators to run our app. Other software required is AWS and GitHub. The knowledge required is Java programming, remote communications (both via bluetooth and via WiFi), usage of Raspberry Pi, experience with cloud and database servers. Other skills and knowledge required is time management, debugging skills, development strategies, and team work.",source:"@site/docs/requirements/general-requirements.md",sourceDirName:"requirements",slug:"/requirements/general-requirements",permalink:"/project-multi-purpose-camera/docs/requirements/general-requirements",draft:!1,editUrl:"https://github.com/Capstone-Projects-2023-Spring/project-multi-purpose-camera/edit/main/documentation/docs/requirements/general-requirements.md",tags:[],version:"current",lastUpdatedBy:"NickCoffin",sidebarPosition:3,frontMatter:{sidebar_position:3},sidebar:"docsSidebar",previous:{title:"System Block Diagram",permalink:"/project-multi-purpose-camera/docs/requirements/system-block-diagram"},next:{title:"Features and Requirements",permalink:"/project-multi-purpose-camera/docs/requirements/features-and-requirements"}},u={},l=[{value:"Hardware Requirements",id:"hardware-requirements",level:2},{value:"Software Requirements",id:"software-requirements",level:2}],c={toc:l};function d(e){let{components:r,...t}=e;return(0,n.kt)("wrapper",(0,a.Z)({},c,t,{components:r,mdxType:"MDXLayout"}),(0,n.kt)("h1",{id:"general-requirements"},"General Requirements"),(0,n.kt)("p",null,"The general requirements for this project corresponds with the hardware aspect and the software aspect. The hardwware requires a Raspberry Pi and the additional hardware accessories (such as the camera, mic, SD card, etc). With working with the Pi, there also needs to be a basic knowledge of what a Pi is, the capabilities, and how it functions. The software required is the Java Development Kit and an IDE capable of syncing to GitHub. For this, Android Studios is used, which conveniently has built in JDKs and emulators to run our app. Other software required is AWS and GitHub. The knowledge required is Java programming, remote communications (both via bluetooth and via WiFi), usage of Raspberry Pi, experience with cloud and database servers. Other skills and knowledge required is time management, debugging skills, development strategies, and team work. "),(0,n.kt)("h2",{id:"hardware-requirements"},"Hardware Requirements"),(0,n.kt)("ul",null,(0,n.kt)("li",{parentName:"ul"},"Raspberry pi"),(0,n.kt)("li",{parentName:"ul"},"Camera compatible with raspberry pi"),(0,n.kt)("li",{parentName:"ul"},"Microphone"),(0,n.kt)("li",{parentName:"ul"},"SD card"),(0,n.kt)("li",{parentName:"ul"},"Power cable for raspberry pi"),(0,n.kt)("li",{parentName:"ul"},"WiFi"),(0,n.kt)("li",{parentName:"ul"},"Power source")),(0,n.kt)("h2",{id:"software-requirements"},"Software Requirements"),(0,n.kt)("ul",null,(0,n.kt)("li",{parentName:"ul"},"Android Studios (SDK needs to be built to get a working emulator)"),(0,n.kt)("li",{parentName:"ul"},"AWS"),(0,n.kt)("li",{parentName:"ul"},"MySQL Database"),(0,n.kt)("li",{parentName:"ul"},"Visual Studios"),(0,n.kt)("li",{parentName:"ul"},"PyCharm"),(0,n.kt)("li",{parentName:"ul"},"Android Emulator (this can be built within Android Studios or physical Android device will work too)")))}d.isMDXComponent=!0}}]);