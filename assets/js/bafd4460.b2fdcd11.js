"use strict";(self.webpackChunkcreate_project_docs=self.webpackChunkcreate_project_docs||[]).push([[9617],{3905:(e,t,r)=>{r.d(t,{Zo:()=>l,kt:()=>v});var n=r(7294);function o(e,t,r){return t in e?Object.defineProperty(e,t,{value:r,enumerable:!0,configurable:!0,writable:!0}):e[t]=r,e}function i(e,t){var r=Object.keys(e);if(Object.getOwnPropertySymbols){var n=Object.getOwnPropertySymbols(e);t&&(n=n.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),r.push.apply(r,n)}return r}function a(e){for(var t=1;t<arguments.length;t++){var r=null!=arguments[t]?arguments[t]:{};t%2?i(Object(r),!0).forEach((function(t){o(e,t,r[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(r)):i(Object(r)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(r,t))}))}return e}function s(e,t){if(null==e)return{};var r,n,o=function(e,t){if(null==e)return{};var r,n,o={},i=Object.keys(e);for(n=0;n<i.length;n++)r=i[n],t.indexOf(r)>=0||(o[r]=e[r]);return o}(e,t);if(Object.getOwnPropertySymbols){var i=Object.getOwnPropertySymbols(e);for(n=0;n<i.length;n++)r=i[n],t.indexOf(r)>=0||Object.prototype.propertyIsEnumerable.call(e,r)&&(o[r]=e[r])}return o}var c=n.createContext({}),d=function(e){var t=n.useContext(c),r=t;return e&&(r="function"==typeof e?e(t):a(a({},t),e)),r},l=function(e){var t=d(e.components);return n.createElement(c.Provider,{value:t},e.children)},u="mdxType",p={inlineCode:"code",wrapper:function(e){var t=e.children;return n.createElement(n.Fragment,{},t)}},m=n.forwardRef((function(e,t){var r=e.components,o=e.mdxType,i=e.originalType,c=e.parentName,l=s(e,["components","mdxType","originalType","parentName"]),u=d(r),m=o,v=u["".concat(c,".").concat(m)]||u[m]||p[m]||i;return r?n.createElement(v,a(a({ref:t},l),{},{components:r})):n.createElement(v,a({ref:t},l))}));function v(e,t){var r=arguments,o=t&&t.mdxType;if("string"==typeof e||o){var i=r.length,a=new Array(i);a[0]=m;var s={};for(var c in t)hasOwnProperty.call(t,c)&&(s[c]=t[c]);s.originalType=e,s[u]="string"==typeof e?e:o,a[1]=s;for(var d=2;d<i;d++)a[d]=r[d];return n.createElement.apply(null,a)}return n.createElement.apply(null,r)}m.displayName="MDXCreateElement"},200:(e,t,r)=>{r.r(t),r.d(t,{assets:()=>c,contentTitle:()=>a,default:()=>u,frontMatter:()=>i,metadata:()=>s,toc:()=>d});var n=r(7462),o=(r(7294),r(3905));const i={sidebar_position:4},a="Features and Requirements",s={unversionedId:"requirements/features-and-requirements",id:"requirements/features-and-requirements",title:"Features and Requirements",description:"Streaming video/audio",source:"@site/docs/requirements/features-and-requirements.md",sourceDirName:"requirements",slug:"/requirements/features-and-requirements",permalink:"/project-multi-purpose-camera/docs/requirements/features-and-requirements",draft:!1,editUrl:"https://github.com/Capstone-Projects-2023-Spring/project-multi-purpose-camera/edit/main/documentation/docs/requirements/features-and-requirements.md",tags:[],version:"current",lastUpdatedBy:"tuj02630",sidebarPosition:4,frontMatter:{sidebar_position:4},sidebar:"docsSidebar",previous:{title:"General Requirements",permalink:"/project-multi-purpose-camera/docs/requirements/general-requirements"},next:{title:"Use-case descriptions",permalink:"/project-multi-purpose-camera/docs/requirements/use-case-descriptions"}},c={},d=[{value:"Streaming video/audio",id:"streaming-videoaudio",level:2},{value:"Pre-made setting profiles (ie. baby monitor, dashcam, etc.)",id:"pre-made-setting-profiles-ie-baby-monitor-dashcam-etc",level:2},{value:"Alerts (From video, sound, etc.)",id:"alerts-from-video-sound-etc",level:2},{value:"Account connection",id:"account-connection",level:2},{value:"Interact with video through app",id:"interact-with-video-through-app",level:2},{value:"Customize resolution and bitrate",id:"customize-resolution-and-bitrate",level:2},{value:"Save video on the cloud",id:"save-video-on-the-cloud",level:2},{value:"Night vision and thermal cameras",id:"night-vision-and-thermal-cameras",level:2},{value:"Sound property detection",id:"sound-property-detection",level:2},{value:"Video property detection",id:"video-property-detection",level:2}],l={toc:d};function u(e){let{components:t,...r}=e;return(0,o.kt)("wrapper",(0,n.Z)({},l,r,{components:t,mdxType:"MDXLayout"}),(0,o.kt)("h1",{id:"features-and-requirements"},"Features and Requirements"),(0,o.kt)("h2",{id:"streaming-videoaudio"},"Streaming video/audio"),(0,o.kt)("p",null,"Stream live video and audio\nStream saved videos and audio\nAccess all saved videos and select between save streams from camera\nChoose between cameras\nSearch through all video quickly with pre-loaded low-bitrate video"),(0,o.kt)("h2",{id:"pre-made-setting-profiles-ie-baby-monitor-dashcam-etc"},"Pre-made setting profiles (ie. baby monitor, dashcam, etc.)"),(0,o.kt)("h2",{id:"alerts-from-video-sound-etc"},"Alerts (From video, sound, etc.)"),(0,o.kt)("p",null,"Create an alerts\nSelect criterion for type: "),(0,o.kt)("ul",null,(0,o.kt)("li",{parentName:"ul"},"Camera: regular, night vision, thermal"),(0,o.kt)("li",{parentName:"ul"},"Brightness: intensity, Color, and length"),(0,o.kt)("li",{parentName:"ul"},"Motion: intensity and length"),(0,o.kt)("li",{parentName:"ul"},"Sound: range, rntensity, and length"),(0,o.kt)("li",{parentName:"ul"},"Alert: notification, alarm"),(0,o.kt)("li",{parentName:"ul"},"Time Range\nAdd a limiter to the alert\nLimit of alerts occurrences over a period of time")),(0,o.kt)("h2",{id:"account-connection"},"Account connection"),(0,o.kt)("p",null,"Connect a camera to account\nSetup camera (connect to wifi, set profile, etc.)"),(0,o.kt)("h2",{id:"interact-with-video-through-app"},"Interact with video through app"),(0,o.kt)("p",null,"Access and view saved videos\nScroll through video quickly or can speed the video up\nDelete saved videos off of their accounts\nDownload the videos from the cloud onto a smartphone\nTake a picture"),(0,o.kt)("h2",{id:"customize-resolution-and-bitrate"},"Customize resolution and bitrate"),(0,o.kt)("p",null,"Users can increase/decrease resolutions and bitrate\nThe resolution and bitrate should default to the best possible option according to the user\u2019s bandwidth\nLive stream and video search is limited to low bitrate"),(0,o.kt)("h2",{id:"save-video-on-the-cloud"},"Save video on the cloud"),(0,o.kt)("p",null,"Create a saving policy\nSelect camera\nSelect resolution\nSelect bit-rate\nSelect max time (or storage)\nAdd criterion (brightness, motion, sound)"),(0,o.kt)("h2",{id:"night-vision-and-thermal-cameras"},"Night vision and thermal cameras"),(0,o.kt)("h2",{id:"sound-property-detection"},"Sound property detection"),(0,o.kt)("p",null,"Enable sound on the app\nSound intensity detection in decibels"),(0,o.kt)("h2",{id:"video-property-detection"},"Video property detection"),(0,o.kt)("p",null,"Motion detection\nLight intensity detection"))}u.isMDXComponent=!0}}]);