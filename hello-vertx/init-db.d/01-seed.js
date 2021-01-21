db = db.getSiblingDB('friends');
db.createCollection('friends');
db.friends.insertMany([
  {
    _id: 1,
    firstName: "Michelle",
    since: "2011"
  },
  {
    _id: 2,
    firstName: "Venkat",
    since: "2010"
  },
  {
    _id: 3,
    firstName: "Matt",
    since: "2006"
  }
])
